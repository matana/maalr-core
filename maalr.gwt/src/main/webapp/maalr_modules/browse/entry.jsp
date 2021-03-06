<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%@ page import="org.springframework.security.openid.OpenIDAttribute" %>
<%@ page import="org.springframework.security.openid.OpenIDAuthenticationToken" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<%@ page import="de.uni_koeln.spinfo.maalr.common.server.util.Configuration" %>
<%@ page import="de.uni_koeln.spinfo.maalr.common.shared.description.LemmaDescription" %>
<%@ page import="de.uni_koeln.spinfo.maalr.common.shared.description.UseCase" %>
<%@ page import="de.uni_koeln.spinfo.maalr.common.shared.LemmaVersion" %>
<%@ page import="de.uni_koeln.spinfo.maalr.lucene.query.QueryResult" %>
<%@ page import="de.uni_koeln.spinfo.maalr.lucene.query.MaalrQuery" %>
<%@ page import="de.uni_koeln.spinfo.maalr.lucene.query.MaalrQueryFormatter" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="<%=session.getAttribute("pl")%>" />
<fmt:setBundle basename="de.uni_koeln.spinfo.maalr.webapp.i18n.text" />

		<%
			MaalrQuery maalrQuery = (MaalrQuery) request.getAttribute("query");
		%>
	    <div class="page-header">
    		<h1><fmt:message key="maalr.dict.entry_header" />&nbsp;<i><%=MaalrQueryFormatter.getQueryLabel(maalrQuery)%></i></h1>
    	</div>
		
		<div class="well" style="text-align: justify;">
			<%
				QueryResult result = (QueryResult) request.getAttribute("result");
				List<LemmaVersion> entries = new ArrayList<LemmaVersion>();
		
				if (maalrQuery != null && result != null) {
					entries = result.getEntries();
					LemmaDescription description = Configuration.getInstance().getLemmaDescription();
					String language = (String) maalrQuery.getValue("language");
			%>
			
			<%=description.toString(MaalrQueryFormatter.getQueryLabel(maalrQuery), entries, description.getLanguageName(true).equals(language))%>
			
			<%
				}
			
			%>
		</div>

		<%	if (maalrQuery != null && result != null && result.getEntries().size() != 0) {  %>
		<% 
				String language = maalrQuery.getValue("language");
		  	 	String phrase = MaalrQueryFormatter.getQueryLabel(maalrQuery);
		%>
			<ul>
				<li id="show_results_noscript">
					Show query results for <a href="${dictContext}/translate.html?values[searchPhrase]=<%=phrase%>"><i><%=phrase%></i></a>
				</li>
				<li id="show_results_script">
					Show query results for <a href="${dictContext}/translate.html#searchPhrase=<%=phrase%>"><i><%=phrase%></i></a>
				</li>
			</ul>
		<% } %>
