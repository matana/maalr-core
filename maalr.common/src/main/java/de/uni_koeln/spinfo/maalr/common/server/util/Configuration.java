/*******************************************************************************
 * Copyright 2013 Sprachliche Informationsverarbeitung, University of Cologne
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.uni_koeln.spinfo.maalr.common.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.LoggerFactory;

import de.uni_koeln.spinfo.maalr.common.server.searchconfig.DictionaryConfiguration;
import de.uni_koeln.spinfo.maalr.common.server.searchconfig.DictionaryConfiguration.UiConfigurations;
import de.uni_koeln.spinfo.maalr.common.shared.ClientOptions;
import de.uni_koeln.spinfo.maalr.common.shared.description.LemmaDescription;
import de.uni_koeln.spinfo.maalr.common.shared.searchconfig.UiConfiguration;

public class Configuration {

	private static final String MAALR_PROPERTIES = "maalr.properties";

	private static final String MAALR_CONFIG_DIR = "maalr.config.dir";

	private static final String MAALR_DEFAULT_CONFIG_DIR = "maalr_config";

	private static final String MAALR_SEARCH_CONFIG = "maalr.search.config";
	
	private static final String CONTEXT_PATH = "maalr.context.path";

	private static final String LUCENE_DIR = "maalr.lucene.dir";

	private static final String LEX_FILE = "lex.file";

	private static final String MONGODB_PORT = "mongodb.port";

	private static final String MONGODB_HOST = "mongodb.host";

	private static final String MONGODB_NAME = "mongodb.name";
	
	private static final String MONGODB_USER_NAME = "mongodb.user.name";
	
	private static final String LONG_NAME = "maalr.long.name";

	private static final String SHORT_NAME = "maalr.short.name";

	private static final String BACKUP_LOCATION = "backup.location";

	private static final String BACKUP_TRIGGER_TIME = "backup.trigger.time";

	private static final String BACKUP_NUMS = "backup.nums";

	private Properties properties;

	private static Configuration instance;

	private ClientOptions clientOptions;

	private DictionaryConfiguration dictConfig;

	private final File configDir;

	public File getConfigDirectory() {
		return configDir;
	}

	public InputStreamReader getConfiguration(String relativePath)
			throws IOException {
		File parent = getConfigDirectory();
		File file = new File(parent, relativePath);
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IOException(e);
		}
		return reader;
	}

	private Configuration() throws IOException {
		String configDir = System.getProperty(MAALR_CONFIG_DIR);
		boolean isDefault = false;
		if (configDir == null) {
			this.configDir = new File(MAALR_DEFAULT_CONFIG_DIR);
			isDefault = true;
		} else {
			this.configDir = new File(configDir);
		}
		if (this.configDir.exists()) {
			LoggerFactory.getLogger(getClass()).info(
					"Using " + (isDefault ? "default " : "")
							+ "configuration in directory "
							+ this.configDir.getAbsolutePath());
		} else {
			LoggerFactory.getLogger(getClass()).error(
					"The " + (isDefault ? "default " : "")
							+ "configuration directory "
							+ this.configDir.getAbsolutePath()
							+ " does not exist!");
		}
		properties = new Properties();
		try (InputStreamReader input = getConfiguration(MAALR_PROPERTIES)) {
			properties.load(input);
			clientOptions = new ClientOptions();
			clientOptions.setShortAppName(getShortName());
			clientOptions.setLongAppName(getLongName());
		} catch (IOException e) {
			throw e;
		}
		try (InputStreamReader reader = getConfiguration(properties
				.getProperty(MAALR_SEARCH_CONFIG))) {
			JAXBContext ctx = JAXBContext
					.newInstance(DictionaryConfiguration.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			dictConfig = (DictionaryConfiguration) unmarshaller
					.unmarshal(reader);
		} catch (JAXBException e) {
			throw new IOException("Failed to parse search configuration files",
					e);
		}
	}

	public synchronized static Configuration getInstance() {
		if (instance == null) {
			try {
				instance = new Configuration();
			} catch (IOException e) {
				throw new RuntimeException(
						"Failed to initialize configuration", e);
			}
		}
		return instance;
	}

	public String getLuceneDir() {
		return properties.getProperty(LUCENE_DIR);
	}

	public String getLexFile() {
		return properties.getProperty(LEX_FILE);
	}

	public int getMongoPort() {
		return Integer.parseInt(properties.getProperty(MONGODB_PORT));
	}

	public String getMongoDBHost() {
		return properties.getProperty(MONGODB_HOST);
	}

	public void setLuceneDir(String dir) {
		properties.put(LUCENE_DIR, dir);
	}

	public LemmaDescription getLemmaDescription() {
		return dictConfig.getLemmaDescription();
	}

	public String getShortName() {
		return properties.getProperty(SHORT_NAME);
	}

	public String getLongName() {
		return properties.getProperty(LONG_NAME);
	}

	public String getDictContext() {
		return properties.getProperty(CONTEXT_PATH);
	}

	public ClientOptions getClientOptions() {
		return clientOptions;
	}

	public DictionaryConfiguration getDictionaryConfig() {
		return dictConfig;
	}

	public String getBackupLocation() {
		return properties.getProperty(BACKUP_LOCATION);
	}

	public String getTriggerTime() {
		return properties.getProperty(BACKUP_TRIGGER_TIME);
	}

	public String getBackupNums() {
		return properties.getProperty(BACKUP_NUMS);
	}

	public UiConfiguration getEditorDefaultSearchUiConfig() {
		UiConfigurations uiConfigs = dictConfig.getUiConfigurations();
		if (uiConfigs == null)
			return null;
		return uiConfigs.getEditorDefaultUiConfiguration();
	}

	public UiConfiguration getEditorExtendedSearchUiConfig() {
		UiConfigurations uiConfigs = dictConfig.getUiConfigurations();
		if (uiConfigs == null)
			return null;
		return uiConfigs.getEditorAdvancedUiConfiguration();
	}

	public UiConfiguration getUserDefaultSearchUiConfig() {
		UiConfigurations uiConfigs = dictConfig.getUiConfigurations();
		if (uiConfigs == null)
			return null;
		return uiConfigs.getUserDefaultUiConfiguration();
	}

	public UiConfiguration getUserExtendedSearchUiConfig() {
		UiConfigurations uiConfigs = dictConfig.getUiConfigurations();
		if (uiConfigs == null)
			return null;
		return uiConfigs.getUserAdvancedUiConfiguration();
	}

	public UiConfiguration[] getUIConfigurations() {
		return new UiConfiguration[] { getUserDefaultSearchUiConfig(),
				getUserExtendedSearchUiConfig(),
				getEditorDefaultSearchUiConfig(),
				getEditorExtendedSearchUiConfig() };
	}

	public String getSocialClientKey(String socialService) {
		return properties.getProperty(socialService + ".consumerKey");
	}

	public String getSocialClientSecret(String socialService) {
		return properties.getProperty(socialService + ".consumerSecret");
	}

	public String getRedirectUrl() {
		return properties.getProperty("social.redirect.page");
	}

	public String getServerInetAddress() {
		return properties.getProperty("maalr.inet.address");
	}

	public String getDbName() {
		return properties.getProperty(MONGODB_NAME);
	}
	
	public String getUserDbName() {
		return properties.getProperty(MONGODB_USER_NAME);
	}
}
