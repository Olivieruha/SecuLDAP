<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Management des groupes</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>	
	<br/>
	<c:forEach items="${listGroup}" var="group">
		<h4 class="text-muted">&nbsp;&nbsp;Groupe ${group.groupName}</h4>
		<a href="/SecuLDAP/helpdesk/addUserToGroup?groupName=${group.groupName}" style="text-decoration:none">&nbsp;&nbsp;&nbsp;Ajouter un membre</a>
		<table class="table table-hover table-condensed">
			<tr>
				<th style="width:33%;">Utilisateur</th>
				<th style="width:33%;">Nom de famille</th>
				<th style="width:33%;">Supprimer du groupe</th>
			</tr>
			<c:forEach items="${group.groupMembers}" var="member">
				<c:if test="${member.fullName!='system administrator'}">			
				<tr>
					<td>${member.fullName}</td>
					<td>${member.lastName}</td>
					<td>
						<a href="/SecuLDAP/helpdesk/removeUserFromGroup?groupName=${group.groupName}&fullName=${member.fullName}">
							<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>						
						</a>
					</td>
				</tr>
			</c:if>	
			</c:forEach>
		</table>
	</c:forEach>
	
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script> 
</body>
</html>