<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="helpdeskheader.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ajout d'un utilisateur</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>	
	<form:form class="form-signin" method="POST" modelAttribute="person" action="/SecuLDAP/helpdesk/editUserProcess">
		<h3 class="text-info text-center"><br/>Modification de l'utilisateur ${person.fullName}<br/></h3>
		<form:hidden path="fullName" value="${person.fullName}"/>
		<form:input class="form-control" path="lastName" placeholder="Nom de famille" value="${person.lastName}"/>
		<form:password class="form-control" path="userPassword" placeholder="Mot de passe"/>
		<c:if test="${not empty editUserMessage}"><h6 class="alert alert-danger text-center">${editUserMessage}</h6></c:if>		
		<c:if test="${not empty validPasswordMessage}"><h6 class="alert alert-danger text-center">${validPasswordMessage}</h6></c:if>
		<form:button class="btn btn-success btn-sm center-block" type="submit">Modifier</form:button>
	</form:form>
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
</body>
</html>