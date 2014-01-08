<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../header.jsp" %>

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
		<c:forEach items="${groups}" var="group">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="text-muted">&nbsp;&nbsp;Groupe ${group.groupName}</h4>
					<form action="/SecuLDAP/admin/addUserToGroup" method="POST">	
						<input type="hidden" name="groupName" value="${group.groupName}" />	
						<button type="submit" role="button" class="btn btn-primary btn-xs">Ajouter un membre</button>
					</form>	
					<form action="/SecuLDAP/admin/deleteGroup" method="POST">	
						<input type="hidden" name="groupName" value="${group.groupName}" />	
						<c:if test="${group.groupName !='admin' and group.groupName !='helpdesk' and group.groupName !='groupmanager'}"><button type="submit" role="button" class="customer btn btn-danger btn-xs">Supprimer le groupe</button></c:if>
					</form>	
				</div>
				<div class="panel-body">			
				<table class="table table-hover table-striped table-condensed">	
					<thead>
						<tr>
							<th>Utilisateur</th>
							<th>Nom de famille</th>
							<th>Supprimer du groupe</th>
						</tr>
					</thead>
					<tbody>							
							<c:forEach items="${group.groupMembers}" var="person">
							<tr>
								<td>${person.fullName}</td>
								<td>${person.lastName}</td>
								<td>
									<a href="/SecuLDAP/admin/removeUserFromGroup?groupName=${group.groupName}&fullName=${person.fullName}">
										<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>
									</a>			
								</td>
				    		</tr>
						</c:forEach>	
					</tbody>
				</table>
				</div>	
			</div>
		</c:forEach>	
	</body>
</html>
