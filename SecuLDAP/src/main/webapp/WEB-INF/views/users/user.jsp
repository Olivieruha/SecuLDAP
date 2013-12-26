<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Page Utilisateur</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>
	<p> Vous êtes : <br/>
		Nom : ${person.lastName} <br/>
		Identifiant : ${person.fullName}
	</p>
	<form:form class="form-signin" method="post" modelAttribute="person" action="/SecuLDAP/user/modifyPasswordForm">
		<form:hidden path="fullName"/>
		<form:hidden path="lastName"/>
		<form:password class="form-control" path="userPassword" placeholder=" Nouveau mot de passe"/>
		<form:errors path="userPassword" cssClass="error"></form:errors>
		<form:button type="submit">Changer le mot de passe</form:button>
	</form:form>
	<a href="<c:url value="/j_spring_security_logout" />" > Se déconnecter</a>
	
	<script src="https://code.jquery.com/jquery.js"></script>
    <script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
</body>
</html>