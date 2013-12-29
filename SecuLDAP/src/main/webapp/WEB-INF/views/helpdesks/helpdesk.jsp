<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
	<br/>
	<div class="contenu" id="usermanagement">
		<h4 class="text-muted">Liste des utilisateurs :</h4>
		<table class="table table-hover">
			<tr>
				<th>Nom d'utilisateur</th>
				<th>Modifier</th>
				<th>Supprimer</th>
				<th>Réinit. mot de passe</th>
			</tr>
			<c:forEach items="${listPerson}" var="person">			
			<tr>
				<td>${person.fullName}</td>
				<td><a data-toggle="modal" data-id="${person.fullName}" data-target="#modalConfirmUpdateUser" class="modalConfirm"><img src="/SecuLDAP/resources/design/img/edit_user.png"></img></a></td>
				<td><a data-toggle="modal" data-id="${person.fullName}" data-target="#modalConfirmDeleteUser" class="modalConfirm"><img src="/SecuLDAP/resources/design/img/remove_user.png"></img></a></td>
				<td><a data-toggle="modal" data-id="${person.fullName}" data-target="#modalConfirmReinitUserPassword" class="modalConfirm"><img src="/SecuLDAP/resources/design/img/reinit_password.png"></img></a></td>
			<tr>
			</c:forEach>
		</table>
	</div>
	
	<script>$(document).on("click", ".modalConfirm", function () {
	     var fullName = $(this).data('id'); 
	     $(".modal-body #fullName").val( fullName );
	});
	</script>
	
	<div class="contenu cache" id ="groupmanagement">
		<h4> Listes des groupes :</h4>
		<ul>
		<c:forEach items="${listGroup}" var="group">
			<li><a href="#">${group.groupName}</a></li>
		</c:forEach>
		</ul>
	</div>
	
	<div class="modal fade" id="modalConfirmDeleteUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form:form class="form-signin" method="post" modelAttribute="person" action="/SecuLDAP/helpdesk/deleteUser">
      	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        	<h4 class="modal-title" id="myModalLabel">Confirmation</h4>
      	</div>
      	<div class="modal-body">
      		<p>Etes-vous sur de vouloir supprimer l'utilisateur ?</p>
 			<input class="text-center center-block" type="text" id="fullName" name="fullName" path="fullName" value="" disabled="disabled"/>
		</div>
      	<div class="modal-footer">
        	<form:button type="button" class="btn btn-default" data-dismiss="modal">Annuler</form:button>
        	<form:button type="submit" class="btn btn-primary">Supprimer</form:button>
      	</div>
      </form:form>
    </div>
  </div>
</div>
	<script src="/SecuLDAP/resources/design/js/bootstrap.min.js"></script>  
	
</body>
</html>