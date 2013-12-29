<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="helpdeskheader.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Helpdesk</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
	<script src="/SecuLDAP/resources/design/js/jquery-1.10.min.js"></script>
	<script src="/SecuLDAP/resources/design/js/contenu.js"></script>
</head>
<body>
	<br/>
	<div class="contenu" id="usermanagement">
		<h4 class="text-muted">Liste des utilisateurs :</h4>
		<table class="table table-hover">
			<tr>
				<th>Nom d'utilisateur</th>
				<th>Modifier</th>
				<th>Supprimer</th>
				<th>Réinit. mot de passe</th>
			</tr>
			<c:forEach items="${listPerson}" var="person">			
			<tr>
				<td>${person.fullName}</td>
				<td>
					<form method="POST" action="/SecuLDAP/helpdesk/editUser">
						<input type="hidden" name="fullName" value="${person.fullName}"/>
						<button type="submit"><img src="/SecuLDAP/resources/design/img/edit_user.png"></img></button>
					</form>				
				</td>
				<td>
					<form method="POST" action="/SecuLDAP/helpdesk/deleteUser">
						<input type="hidden" name="fullName" value="${person.fullName}"/>
						<button type="submit"><img src="/SecuLDAP/resources/design/img/remove_user.png"></img></button>
					</form>					
				</td>
				<td>
					<form method="POST" action="/SecuLDAP/helpdesk/reinitPassword">
						<input type="hidden" name="fullName" value="${person.fullName}"/>
						<button type="submit"><img src="/SecuLDAP/resources/design/img/reinit_password.png"></img></button>
					</form>	
				</td>
			<tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="contenu cache" id ="groupmanagement">
		<h4> Listes des groupes :</h4>
		<ul>
		<c:forEach items="${listGroup}" var="group">
			<li><a href="#">${group.groupName}</a></li>
		</c:forEach>
		</ul>
	</div>
	
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script> 
</body>
</html>