<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Locale" %>

<%-- HTML HEADER --%>
<jsp:include page="/maalr_modules/misc/htmlhead.jsp" />

	<body>
		
		<%-- NAVIGATION --%>
		<div id="top"><jsp:include page="/maalr_modules/misc/header.jsp" /></div>

		<%-- CONTENT --%>		
		<div id="content">
		
			<%@ include file="/maalr_modules/misc/language_widget.jsp"%>
			<%@ include file="/maalr_modules/misc/login_widget.jsp"%>
			
			<div class="container well information_container">
	
				<%
					String languageTag = (String) session.getAttribute("pl");
					Locale locale = Locale.forLanguageTag(languageTag);
				%>
				<% 
					if(languageTag.equals("ru")){
				%>
					<jsp:include page="/static/json-ru.jsp" />
				<% 
					} else if(languageTag.equals("de")){
				%>
					<jsp:include page="/static/json-de.jsp" />
				<% 
					} else {
				%>
					<h1>Nothing to display, please contact us!</h1>
				<% 
					}
				%>
			</div>
		</div>
		
		<%-- FOOTER --%>
		<div id="bottom"><jsp:include page="/maalr_modules/misc/footer.jsp" /></div>
		
	</body>
</html>