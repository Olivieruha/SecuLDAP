<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../header.jsp" %>

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
			<h4 class="text-muted">&nbsp;&nbsp;Groupe ${g.groupName}
				<a href="/SecuLDAP/groupmanager/removeGroup?groupName=${g.groupName}" style="text-decoration:none">&nbsp;
					<img src="/SecuLDAP/resources/design/img/delete_group.png"/>
				</a>
			</h4>
			<a href="/SecuLDAP/groupmanager/addUserToGroup?groupName=${g.groupName}" style="text-decoration:none">&nbsp;&nbsp;&nbsp;Ajouter un membre</a>
			<table class="table table-hover table-condensed">	
				<thead>
					<tr>
						<th style="width:33%;">Utilisateur</th>
						<th style="width:33%;">Nom de famille</th>
						<th style="width:33%;">Supprimer du groupe</th>
					</tr>
				</thead>
				<tbody>							
						<c:forEach items="${g.groupMembers}" var="person">
						<tr>
							<td>${person.fullName}</td>
							<td>${person.lastName}</td>
							<td>
								<a href="/SecuLDAP/groupmanager/removeUserFromGroup?groupName=${g.groupName}&fullName=${person.fullName}">
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