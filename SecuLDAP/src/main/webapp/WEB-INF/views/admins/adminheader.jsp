<!-- Fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
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
        <li><a class="lien-contenu" href="/SecuLDAP/admin/">Gestion des groupes</a></li>
        <li><a class="lien-contenu" href="/SecuLDAP/groupmanager/addGroup">Cr�ation d'un groupe</a></li>
        <li><a class="lien-contenu" href="/SecuLDAP/groupmanager/addGroup">Cr�ation d'un utilisateur</a></li>     
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="<c:url value="/j_spring_security_logout" />">Se d�connecter</a></li>
     </ul>
   </div>
  </div>
</div>
