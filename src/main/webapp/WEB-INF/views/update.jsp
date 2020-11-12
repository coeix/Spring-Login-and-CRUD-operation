<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<style type="text/css">
     		<%@include file="css/style.css" %>
		</style>
		<title>Aggiorna utente</title>
	</head>
	<body>
		<div>
			<h3>Aggiorna Utente:</h3>
			<form:form method="POST" action="${pageContext.request.contextPath}/updateOldUser" modelAttribute="userToUpdate">
				<form:input type="hidden" path="id" />
				<table>
					<tr>
						<td><form:label path="name">Name</form:label></td>
						<td><form:input path="name" /></td>
					</tr>
					<tr>
						<td><form:label path="surname">Surname</form:label></td>
						<td><form:input path="surname" /></td>
					</tr>
					<tr>
						<td><form:label path="email">Email</form:label></td>
						<td><form:input path="email" /></td>
					</tr>
					<tr>
						<td><form:label path="password">Password</form:label></td>
						<td><form:input path="password" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit" /></td>
						<td><input type="reset" value="Reset"><br></td>
					</tr>
				</table>
			</form:form>
		</div>
	</body>
</html>