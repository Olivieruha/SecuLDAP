<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="userheader.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Page Utilisateur</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
	<script src="/SecuLDAP/resources/design/js/jquery-1.10.min.js"></script>
	<script src="/SecuLDAP/resources/design/js/contenu.js"></script>	
</head>
<body>
	<div class="contenu" id="accueil">
		<form:form class="form-signin" method="post" modelAttribute="person" action="/SecuLDAP/user/modifyPasswordForm">
			<h3 class="text-info text-center"><br/> Bienvenue ${person.fullName} !<br/></h3>
			<form:hidden path="fullName"/>
			<form:hidden path="lastName"/>
			<form:password class="form-control" path="userPassword" placeholder=" Nouveau mot de passe"/>
			<h6 class="alert alert-danger text-center">${passwordUpdateMessage}</h6>	
			<form:button class="btn btn-success btn-sm center-block" type="submit">Changer le mot de passe</form:button>
		</form:form>
	</div>			

	<div class="contenu cache jumbotron" id="apropos">
		<p>L'a propos</p>
	</div>
	
	<div class="contenu cache jumbotron" id="contact">
		<p>Les contacts</p>
	</div>
	
    <script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
</body>
</html>