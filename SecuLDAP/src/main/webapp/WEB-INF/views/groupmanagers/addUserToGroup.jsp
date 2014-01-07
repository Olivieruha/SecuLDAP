<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="groupmanagerheader.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ajout d'un utilisateur</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>	
	<br/>
	<h4 class="text-muted">&nbsp;Liste des utilisateurs disponibles :</h4>
	<table class="table table-hover">
		<tr>
			<th>Utilisateur</th>
			<th>Nom de famille</th>
			<th>Ajouter au groupe ${groupName}</th>
		</tr>
		<c:forEach items="${listPerson}" var="person">
			<c:if test="${person.fullName!='system administrator'}">			
				<tr>
					<td>${person.fullName}</td>
					<td>${person.lastName}</td>
					<td>
						<a href="/SecuLDAP/helpdesk/addUserToGroupProcess?fullName=${person.fullName}&groupName=${groupName}">
							<img src="/SecuLDAP/resources/design/img/add_user_group.png"></img>
						</a>			
					</td>
				</tr>
			</c:if>	
		</c:forEach>
	</table>
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
</body>
</html>