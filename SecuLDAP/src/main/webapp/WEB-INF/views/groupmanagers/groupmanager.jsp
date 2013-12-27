
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Page Utilisateur</title>
<link rel="stylesheet" type="text/css"
	href="/SecuLDAP/resources/design/css/signin.css" />
<link rel="stylesheet" type="text/css"
	href="/SecuLDAP/resources/design/css/bootstrap.min.css" />
<script src="/SecuLDAP/resources/design/js/jquery-1.10.min.js"></script>
<script src="/SecuLDAP/resources/design/js/contenu.js"></script>
<title>GroupManager page</title>
</head>
<body>

	<a href="<c:url value="j_spring_security_logout" />"> Logout</a>
	<table class="table table-striped table-bordered table-condensed"
		data-provides="rowlink">
		<tr>
			<th>Groupes</th>
			<th>Membres</th>
		</tr>
		<c:forEach items="${groups}" var="g">
			<tr>
				<th>${g.groupName}</th>
				<c:forEach items="${g.groupMembers}" var="person">
					<th>${person.fullName}</th>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</body>
</html>