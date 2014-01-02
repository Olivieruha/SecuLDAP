<!-- Fixed navbar -->
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
        <li><a class="lien-contenu" href="/SecuLDAP/helpdesk/userManagement">Gestion des utilisateurs</a></li>
        <li><a class="lien-contenu" href="/SecuLDAP/helpdesk/groupManagement">Gestion des groupes</a></li> <!--  #Id donné dans la classe JSP -->
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="<c:url value="/j_spring_security_logout" />">Se déconnecter</a></li>
     </ul>
   </div>
  </div>
</div>
