<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<title>Home Page</title>
		<style type="text/css">
     		<%@include file="css/style.css" %>
		</style>
	</head>
	<body>
		<div>
			<!-- Function public ModelAndView displayNewUserForm() in UserController.java -->
			<h1 class="center">${headerMessage}</h1>
			
			<!-- di seguito, questa struttura c:if non è veramente necessaria al fine di vedere o meno la scritta
			contenuta all'interno della variabile message. E' giusto per introdurre questo strumento che potrebbe 
			tornare utile negli step successivi -->
			<c:if test="${message != null}">  
				<br>
				<h4>${message}</h4>
			</c:if>
			<c:if test="${message == null}"> 
				<br> 
				<h3>Login:</h3>
			</c:if>
			
			<form:form method="POST" action="login" modelAttribute="user">
				<table>
					<tr>
						<td><form:label path="email">Email</form:label></td>
						<td><form:input path="email" /></td>
					</tr>
					<tr>
						<td><form:label path="password">Password</form:label></td>
						<td><form:input path="password" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit"/></td>
						<td><input type="reset" value="Reset"><br></td>
					</tr>
				</table>
				<br>
				<c:if test="${user.getEmail() == null}">  
					<p class="subtitle">
						<!-- function public ModelAndView saveNewUser(@ModelAttribute User user) in UserController.java -->
						Don't have an account? <a href="${pageContext.request.contextPath}/addNewUser">Sign in</a>
					</p>
				</c:if>
				<c:if test="${user.getEmail() != null}">  
					<p class="subtitle">
						You are not <c:out value="${user.getName()}"/>? 
						<a href="${pageContext.request.contextPath}/home">Log in with a different account</a>
					</p>
					<c:set var="user" value="${null}"/>
				</c:if>
			</form:form>
			
			
		</div>
	</body>
</html>