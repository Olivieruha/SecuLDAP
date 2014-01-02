<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="groupmanagerheader.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ajout d'un groupe</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>	
	<br/>
	
	<form:form class="form-signin" action="/SecuLDAP/groupmanager/addGroupProcess">
		<h3 class="text-info text-center"><br/>Formulaire d'un groupe<br/></h3>
		<input class="form-control" path="groupName" type="text" name="groupName" placeholder="Nom du groupe"/>
		<button class="btn btn-success btn-sm center-block" type="submit">Ajouter</button>
	</form:form>	
	 
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
</body>
</html>