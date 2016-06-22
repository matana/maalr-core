<%@ page import="de.uni_koeln.spinfo.maalr.login.LoginManager" %>

<%@ taglib prefix='cr' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="<%=session.getAttribute("pl") %>" />
<fmt:setBundle basename="de.uni_koeln.spinfo.maalr.webapp.i18n.text" />

<cr:choose>
    <cr:when test="${user != null}">
		<a style="color:#6FB8CE;" id="maalr-current-user" href="<cr:url value='/j_spring_security_logout'/>">
			<fmt:message key="maalr.user.logout">
				<fmt:param>${user.getDisplayName()}</fmt:param>
			</fmt:message> 
		</a>
    </cr:when>
    <cr:otherwise>
		<a style="color:#6FB8CE;" href="${dictContext}/login.html">
			<fmt:message key="maalr.user.login" />
		</a>
    </cr:otherwise>
</cr:choose>
