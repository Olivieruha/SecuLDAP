<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	<div class="contenu" id="usermanagement">
		<c:forEach items="${listPerson}" var="person">
			<h2>${person.fullName}</h2>
		</c:forEach>	
	</div>

	<div class="contenu cache" id ="groupmanagement">
		<c:forEach items="${listGroup}" var="group">
			<h2>${group.groupName}</h2>
		</c:forEach>
	</div>
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
	
</body>
</html>