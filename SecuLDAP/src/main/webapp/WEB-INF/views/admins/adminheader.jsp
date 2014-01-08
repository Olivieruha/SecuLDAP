<!-- Fixed navbar -->
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><strong>Annuaire LDAP</strong></a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
      	<li><a href="/SecuLDAP/admin/userManagement"><span class="glyphicon glyphicon-th"></span> Gestion des utilisateurs</a></li>
        <li><a href="/SecuLDAP/admin/groupManagement"><span class="glyphicon glyphicon-tasks"></span> Gestion des groupes</a></li>
      	<li><a href="/SecuLDAP/admin/addUser"><span class="glyphicon glyphicon-plus"></span> Création d'un utilisateur</a></li>
        <li><a href="/SecuLDAP/admin/addGroup"><span class="glyphicon glyphicon-plus-sign"></span> Création d'un groupe</a></li>            
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="#"><span class="glyphicon glyphicon-user"></span> <s:authentication property="principal.username"/></a></li>
        <li><a href="<c:url value="/j_spring_security_logout" />">Se déconnecter</a></li>
     </ul>
   </div>
  </div>
</div>