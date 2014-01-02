
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<th>
		<a href="<c:url value="j_spring_security_logout" />"> Logout</a><br/>
	</th>
	<a href="/SecuLDAP/groupmanager/addGroup">&nbsp;Ajouter un groupe</a>
		<c:forEach items="${groups}" var="g">
			<th><b>${g.groupName}</b>
			<form action="/SecuLDAP/groupmanager/removegroup" method="POST">	
				<input type="hidden" name="groupName" value="${g.groupName}" />	
				<button type="submit" role="button" class="customer btn btn-danger btn-xs">Supprimer le groupe</button>
			</form>		
			</th>
			<table class="table table-hover table-condensed">	
				<thead>
					<tr>
						<th>Utilisateur</th>
						<th>Nom de famille</th>
						<th>Modifier</th>
						<th>Supprimer du groupe</th>
					</tr>
				</thead>
				<tbody>							
						<c:forEach items="${g.groupMembers}" var="person">
						<tr>
							<td>${person.fullName}</td>
							<td>${person.lastName}</td>
							<td>
								<a href="/SecuLDAP/groupmanager/edituser?fullName=${g.groupName}&fullName=${person.fullName}" >
									<img src="/SecuLDAP/resources/design/img/edit_user.png"></img>
								</a>			
							</td>
							<td>
								<a href="/SecuLDAP/groupmanager/removeuser?fullName=${g.groupName}&fullName=${person.fullName}" >
									<img src="/SecuLDAP/resources/design/img/remove_user.png"></img>
								</a>			
							</td>
			    		</tr>
						</c:forEach>	
				<form modelAttribute="person" action="/SecuLDAP/groupmanager/adduser" method="POST">
					<input type="hidden" name="groupName" value="${g.groupName}" />	
					<th><input path="fullName" type="text" name="fullName" class="form-control-fix" placeholder="User fullName"/></th>
					<th><input path="lastName" type="text" name="lastName" class="form-control-fix" placeholder="User lastName"/></th>
					<th><button type="submit" role="button" class="btn btn-primary btn-xs"">Ajouter au groupe</button></th>
					<th></th>
				</form>
				</tbody>
			</table>
		</c:forEach>
		
	<form:form action="/SecuLDAP/groupmanager/addgroup">	
		<input  path="groupName" type="text" name="groupName" placeholder="Group Name"/>
		<button type="submit" role="button" class="btn btn-primary btn-xs">Ajouter le groupe</button>
	</form:form>	
</body>
</html>