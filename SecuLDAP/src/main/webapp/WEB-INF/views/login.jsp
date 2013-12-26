<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Identification</title>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/signin.css"/>
	<link rel="stylesheet" type="text/css" href="/SecuLDAP/resources/design/css/bootstrap.min.css"/>
</head>
<body>		
	<form class="form-signin" method="post" action="j_spring_security_check">
        <h2 class="form-signin-heading">Veuillez vous identifier</h2>
        <input type="text" name="username" class="form-control" placeholder="Nom d'utilisateur (prénom.nom)" required autofocus/>
        <input type="password" name="password" class="form-control" placeholder="Mot de passe" required/>
        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      		<h4 style="color:red">Identifiant ou mot de passe incorrect !</h4>
      	</c:if>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Connexion</button>
      </form>
      
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>      
</body>
</html>