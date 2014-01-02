<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="adminheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Administrateur</title>
		<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css" />
		<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css" />
		<script src="/SecuLDAP/resources/design/js/jquery-1.10.min.js"></script>
		<script src="/SecuLDAP/resources/design/js/contenu.js"></script>
    </head>
    <body>
		<br/>
		<a href="<c:url value="j_spring_security_logout" />"> Logout</a>
		<br/>
		<a href="/SecuLDAP/admin/addGroup">&nbsp;Ajouter un groupe</a>
		<br/>
			<c:forEach items="${groups}" var="group">
				<hr/>
				<h4 class="text-muted">&nbsp;&nbsp;Groupe ${group.groupName}</h4>
				<a href="/SecuLDAP/admin/addUserToGroup?groupName=${group.groupName}">&nbsp;&nbsp;&nbsp;Ajouter un membre</a>	
				</th>
				<table class="table table-hover table-condensed">	
					<thead>
						<tr>
							<th>Utilisateur</th>
							<th>Nom de famille</th>
							<th>Modifier</th>
							<th>Réinit. mot de passe</th>
							<th>Supprimer du groupe</th>
							<th>Supprimer l'utilisateur</th>
						</tr>
					</thead>
					<tbody>							
							<c:forEach items="${group.groupMembers}" var="person">
							<tr>
								<td>${person.fullName}</td>
								<td>${person.lastName}</td>
								<td>
									<a href="/SecuLDAP/admin/edituser?groupName=${group.groupName}&fullName=${person.fullName}" >
										<img src="/SecuLDAP/resources/design/img/edit_user.png"></img>
									</a>			
								</td>
								<td>
									<a href="/SecuLDAP/admin/reinitPassword?fullName=${person.fullName}&lastName=${person.lastName}">
									<img src="/SecuLDAP/resources/design/img/reinit_password.png"></img>
									</a>
								</td>
								<td>
									<a href="/SecuLDAP/admin/removeUserFromGroup?groupName=${group.groupName}&fullName=${person.fullName}" 
									onclick="<c:if test="${fn:length(group.groupMembers) <= 1}">alert('Impossible de supprimer le dernier membre d\'un groupe !')</c:if>">
										<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>
									</a>			
								</td>
								<td>
									<a href="/SecuLDAP/admin/deleteUser?groupName=${group.groupName}&fullName=${person.fullName}" 
									onclick="<c:if test="${fn:length(group.groupMembers) <= 1}">alert('Impossible de supprimer le dernier membre d\'un groupe !')</c:if>">
										<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>
									</a>			
								</td>
				    		</tr>
							</c:forEach>	
					</form>
					</tbody>
				</table>
			</c:forEach>
		<form:form action="/SecuLDAP/admin/addGroup">	
			<input  path="groupName" type="text" name="groupName" placeholder="Group Name"/>
			<button type="submit" role="button" class="btn btn-primary btn-xs">Ajouter le groupe</button>
		</form:form>	
	</body>
</html>
