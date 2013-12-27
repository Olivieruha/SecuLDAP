$(function(){
	//On utilise . pour selectionner des noeuds par leurs classes
	//On utilise # pour selectionner un noeud par son id
	$('.lien-contenu').click(function(){
		var id = $(this).attr('href');
		if($(id).is(':hidden')){ //Si il est caché on le montre
			$('.contenu').hide(500); //Va prendre 500 ms pour cacher tous les articles
			$(id).show(500); //On va recuperer le contenu de href du lien-article sur lequel on vient de cliquer qui correspond à l'id de l'article à montrer
		}
		
		return false; //Pour supprimer l'action par defaut du lien quand on le clique (la redirection)
		})
})