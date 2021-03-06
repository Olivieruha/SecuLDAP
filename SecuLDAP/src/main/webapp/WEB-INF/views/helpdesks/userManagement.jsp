<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Helpdesk</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>
	<br/>
	<h4 class="text-muted">&nbsp;Liste des utilisateurs :</h4>
	<c:if test="${not empty param.onlyGroupMemberMessage}"><h5 class="alert alert-danger text-center">${param.onlyGroupMemberMessage} : impossible de le supprimer !</h5></c:if>
	
	<table class="table table-hover">
		<tr>
			<th style="width:25%;">Utilisateur</th>
			<th style="width:25%;">Nom de famille</th>
			<th style="width:25%;">Modifier</th>
			<th style="width:25%;">R�init. mot de passe</th>
		</tr>
		<c:forEach items="${listPerson}" var="person">
			<c:if test="${person.fullName!='system administrator'}">			
				<tr>
					<td>${person.fullName}</td>
					<td>${person.lastName}</td>
					<td>
						<a href="/SecuLDAP/helpdesk/editUser?fullName=${person.fullName}&lastName=${person.lastName}">
							<img src="/SecuLDAP/resources/design/img/edit_user.png"></img>
						</a>			
					</td>
					<!--<td>
						<a href="/SecuLDAP/helpdesk/deleteUser?fullName=${person.fullName}">
							<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>
						</a>			
					</td>-->
					<td>
						<a href="/SecuLDAP/helpdesk/reinitPassword?fullName=${person.fullName}&lastName=${person.lastName}">
							<img src="/SecuLDAP/resources/design/img/reinit_password.png"></img>
						</a>
					</td>
				</tr>
			</c:if>	
		</c:forEach>
	</table>
	
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script> 
</body>
</html>