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
package de.uni_koeln.spinfo.maalr.services.admin.shared;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uni_koeln.spinfo.maalr.common.shared.LightUserInfo;
import de.uni_koeln.spinfo.maalr.common.shared.Role;
import de.uni_koeln.spinfo.maalr.mongo.exceptions.InvalidUserException;

@RemoteServiceRelativePath("rpc/user")
public interface UserService extends RemoteService {
	
	public @ResponseBody LightUserInfo getUserInfo(String login);
	
	public void updateRole(String login, Role role) throws InvalidUserException;
	
	public void updateUserFields(LightUserInfo user) throws InvalidUserException;
	
	public int getNumberOfUsers();
	
	List<LightUserInfo> getAllUsers(Role role, String text, 
			String sortColumn, boolean sortAscending, int from, int length);
	
	public LightUserInfo insertNewUser(LightUserInfo user) throws InvalidUserException;

	void adminUpdate(LightUserInfo workingCopy) throws InvalidUserException;

	boolean deleteUser(LightUserInfo unmodified);
	
}
