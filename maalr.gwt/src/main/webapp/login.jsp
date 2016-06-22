<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@	page import="java.util.ArrayList" %>
<%@	page import="java.util.List" %>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<fmt:setLocale value="<%=session.getAttribute("pl")%>" />
<fmt:setBundle basename="de.uni_koeln.spinfo.maalr.webapp.i18n.text" />

<%-- HTML HEADER --%>
<jsp:include page="/maalr_modules/misc/htmlhead.jsp" />

	<body>
		
		<%-- NAVIGATION --%>
		<jsp:include page="/maalr_modules/misc/header.jsp" />
		
		<div>
		
			<%-- INTERNAL SIGN IN --%>
			<div class="container" style="margin-top:150px;">
				<h1><fmt:message key="maalr.login.header" /></h1>
				<%-- login info text  --%>
				<div id="maalr_login_info">
					<p style="margin:0px;"><span class="glyphicon icon-info-sign"> </span><fmt:message key="maalr.login.welcome" /></p>
				</div>
				<hr>
				<c:if test="${not empty param.login_error}">
					<p id="error_font"> <fmt:message key="maalr.login.error" /><br /></p>
				</c:if>
				<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
					<div id="login_input">
						<div class="input_wrapper">
							<label for="uname"><fmt:message key="maalr.login.name"/></label>
							<input id="uname" type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
						</div>
						<div class="input_wrapper">
							<label for="upwd"><fmt:message key="maalr.login.pwd"/></label>
							<input id="upwd" type='password' name='j_password'>
						</div>
						<div class="button_wrapper">
							<input class="btn" name="submit" type="submit" value=<fmt:message key="maalr.login.sendButton"/>>
						</div>
					</div>
				</form>
				<hr>
				<%-- OPEN-ID SIGN IN --%>
				<div id="openid_login"> 
					<div class="input_wrapper">
						<form action="<c:url value="/signin/google" />" method="POST">
							<input type="hidden" name="scope" value="email" />
							<button type="submit" id="google_signin"><%--<fmt:message key="maalr.login.google"/>--%>
							<img src="${dictContext}/assets/img/google.png" alt="google_signin" width="40px" height="40px"/>
							</button>
						</form>
					</div>
					<div class="input_wrapper">
						<form action="<c:url value='j_spring_openid_security_check'/>" method="post">
							<input name="openid_identifier" type="hidden" value="https://me.yahoo.com/" />
							<button type="submit" value="${signInYahoo}" id="yahoo_signin"><%--<fmt:message key="maalr.login.yahoo"/>--%>
							<img src="${dictContext}/assets/img/yahoo.png" alt="yahoo_signin" width="40px" height="40px"/>
							</button>
						</form>
					</div>
					<div class="input_wrapper">
						<form action="<c:url value="/signin/facebook" />" method="POST">
							<input type="hidden" name="scope" value="email,publish_stream,offline_access" />
							<button type="submit" id="facebook_signin"><%--<fmt:message key="maalr.login.facebook"/>--%>
							<img src="${dictContext}/assets/img/facebook.png" alt="facebook_signin" width="40px" height="40px"/>
							</button>
						</form>
					</div>
					<div class="input_wrapper">
						<form action="<c:url value="/signin/twitter" />" method="POST">
							<button type="submit" id="twitter_signin"><%--<fmt:message key="maalr.login.twitter"/>--%>
							<img src="${dictContext}/assets/img/twitter.png" alt="twitter_signin" width="40px" height="40px"/>
							</button>
						</form>
					</div> 
					<div class="input_wrapper">
						<form action="<c:url value="/signin/linkedin" />" method="POST">
							<button type="submit" id="linkedin_signin"><%--<fmt:message key="maalr.login.twitter"/>--%>
							<img src="${dictContext}/assets/img/linkedin.png" alt="linkedin_signin" width="40px" height="40px"/>
							</button>
						</form>
					</div> 
					<div class="input_wrapper">
						<button type="submit" id="persona_signin"><%--<fmt:message key="maalr.login.persona"/>--%>
						<img src="${dictContext}/assets/img/persona.png" alt="persona_signin" />
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<%-- FOOTER --%>
		<jsp:include page="/maalr_modules/misc/footer.jsp" />
		
	</body>
</html>