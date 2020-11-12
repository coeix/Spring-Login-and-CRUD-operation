<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"
	import="com.spring.login.entity.User"
	import="antlr.collections.List"
	import="java.util.ArrayList"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<title>Profile</title>
		<style type="text/css">
     		<%@include file="css/style.css"%>
		</style>
	</head>
	<body>
		<div>
			<h1>Benvenuto <c:out value="${userLogged.getName()}"/>  <c:out value="${userLogged.getSurname()}"/></h1>
			<p>la tua email è: <c:out value="${userLogged.getEmail()}"/></p>
			<p>A quanto pare sei Admin della pagina e puoi vedere tutto quanto</p>
		</div>
		<br/>
		<div>
			<c:if test="${showThem==false}">
				<a href="${pageContext.request.contextPath}/findAll">Visualizza Utenti</a>
			</c:if>
			<c:if test="${showThem==true}">
				<c:if test="${!empty usersList}">
					<h3>Utenti registrati:</h3>
					<br/>
	   				<table class="border">
	   					<tr class="border">
	   						<th>Nome</th>
	   						<th>Cognome</th>
	   						<th>Email</th>
	   						<th>Password</th>
	   						<th>Elimina</th>
	   						<th>Modifica</th>
	   					</tr>
	   					<c:forEach items="${usersList}" var="person">
							<tr class="border">
								<td>${person.name}</td>
								<td>${person.surname}</td>
								<td>${person.email}</td>
								<td>${person.password}</td>
								<td> 
									<form:form action="${pageContext.request.contextPath}/delete/${person.id}" method="POST"> 
											<button type = "submit">Delete</button> 
									</form:form>
								</td>
								<td> 
									<form:form action="${pageContext.request.contextPath}/update/${person.id}" method="POST">
											<button type = "submit">Update</button> 
									</form:form>
								</td>	
							</tr>
						</c:forEach>
	   				</table>
	   				
				</c:if>
				<c:if test="${empty usersList}">
					<h3>Qualcosa è andato storto</h3>
					<br/>
				</c:if>
			</c:if>
			<br/>
			<a href="${pageContext.request.contextPath}/addNewUser">Aggiungi nuovo Utente</a>
			<br/>
			
		</div>
	</body>
</html>