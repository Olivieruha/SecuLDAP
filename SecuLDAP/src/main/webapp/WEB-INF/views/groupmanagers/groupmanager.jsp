<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="groupmanagerheader.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Group Manager</title>
<link rel="stylesheet" type="text/css"
	href="/SecuLDAP/resources/design/css/signin.css" />
<link rel="stylesheet" type="text/css"
	href="/SecuLDAP/resources/design/css/bootstrap.min.css" />
<script src="/SecuLDAP/resources/design/js/jquery-1.10.min.js"></script>
<script src="/SecuLDAP/resources/design/js/contenu.js"></script>
<title>Group Manager page</title>

</head>

<body>
	<br/>
	<br/>
		<c:forEach items="${groups}" var="g">
			<h4 class="text-muted">&nbsp;&nbsp;Groupe ${g.groupName}</h4>
			<a href="/SecuLDAP/groupmanager/addUserToGroup?groupName=${g.groupName}">&nbsp;&nbsp;&nbsp;Ajouter un membre</a>
			<form action="/SecuLDAP/groupmanager/removeGroup" method="POST">	
				<input type="hidden" name="groupName" value="${g.groupName}" />	
				<button type="submit" role="button" class="customer btn btn-danger btn-xs">Supprimer le groupe</button>
			</form>		
			<table class="table table-hover table-condensed">	
				<thead>
					<tr>
						<th>Utilisateur</th>
						<th>Nom de famille</th>
						<th>Supprimer du groupe</th>
					</tr>
				</thead>
				<tbody>							
						<c:forEach items="${g.groupMembers}" var="person">
						<tr>
							<td>${person.fullName}</td>
							<td>${person.lastName}</td>
							<td>
								<a href="/SecuLDAP/groupmanager/removeUserFromGroup?groupName=${g.groupName}&fullName=${person.fullName}" 
								onclick="<c:if test="${fn:length(g.groupMembers) <= 1}">alert('Impossible de supprimer le dernier membre d\'un groupe !')</c:if>">
									<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>
								</a>			
							</td>
			    		</tr>
						</c:forEach>	
				</tbody>
			</table>
		</c:forEach>	
</body>
</html>