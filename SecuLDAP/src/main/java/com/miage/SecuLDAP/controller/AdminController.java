package com.miage.SecuLDAP.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.model.Group;
import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class AdminController {
	
	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!?]).{8,20})";
	private Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	private Matcher matcher;

	@RequestMapping(value="/admin/userManagement")
	public ModelAndView admin() {
		return new ModelAndView("admins/userManagement").addObject("listPerson", personService.findAllPerson());
	}
	
	@RequestMapping(value="/admin/groupManagement")
	public ModelAndView groupManagement() {
		return new ModelAndView("admins/groupManagement").addObject("groups", groupService.findAllGroup());
	}
	
	/**
	 * Permet de constuire la page de création d'un utilisateur
	 * @param person L'utilisateur à créer
	 * @return La vue de création d'un utilisateur
	 */
	@RequestMapping(value="/admin/addUser", method=RequestMethod.GET)
	public ModelAndView addUser(Person person) {
		return new ModelAndView("admins/addUser").addObject("person", person);
	}
	
	/**
	 * Permet de créer un utilisateur
	 * @param person L'utilisateur à créer
	 * @return Si création valide, la redirection vers la gestion des utilisateurs sinon la vue de création de l'utilisateur
	 */
	@RequestMapping(value="/admin/addUserProcess", method=RequestMethod.POST)
	public ModelAndView addUserProcess(@ModelAttribute Person person) {
		// Messages d'alertes si un des champs est mal renseigné
		String createUserMessage = "";
		String validPasswordMessage ="";
		boolean isValid = true;
		// Construction des messages d'alertes...
		if(person.getFullName().isEmpty() || person.getFullName() == null) {
			createUserMessage+="Merci de saisir un nom d'utilisateur.\n";
			isValid = false;
		}
		if(person.getLastName().isEmpty() || person.getLastName() == null) {
			createUserMessage+="Merci de saisir un nom de famille.";
			isValid = false;
		}
		// Vérifie que le mot de passe remplit les conditions nécessaires
		matcher = pattern.matcher(person.getUserPassword());
		if(!matcher.matches()) {
			validPasswordMessage ="Le mot de passe doit être composé d'au moins 8 caractères et contenir "
					+ "une majuscule, un chiffre et un caractère spécial parmi @#$%!?";
			isValid = false;
		}
		if(!isValid)
			return new ModelAndView("admins/addUser").addObject("createUserMessage", createUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Création de l'utilisateur et redirection
		personService.createPerson(person);
		return new ModelAndView("redirect:/admin/userManagement");
	}

	/**
	 * Permet de contruire la page de modification d'un utilisateur
	 * @param person L'utilisateur à modifier
	 * @return La vue d'édition de l'utilisateur
	 */
	@RequestMapping(value="/admin/editUser", method=RequestMethod.GET)
	public ModelAndView editUser(Person person) {
		return new ModelAndView("admins/editUser").addObject("person", person);
	}
		
	/**
	 * Permet de modifier un utilisateur
	 * @param person L'utilisateur à modifier
	 * @return Si modification valide, la redirection vers la gestion des utilisateurs sinon la vue d'édition de l'utilisateur
	 */
	@RequestMapping(value="/admin/editUserProcess", method=RequestMethod.POST)
	public ModelAndView editUserProcess(@ModelAttribute Person person) {
		// Messages d'alertes si un des champs est mal renseigné
		String editUserMessage = "";
		String validPasswordMessage ="";
		boolean isValid = true;
		// Construction des messages d'alertes...
		if(person.getFullName().isEmpty() || person.getFullName() == null) {
			editUserMessage+="Merci de saisir un nom d'utilisateur.\n";
			isValid = false;
		}
		if(person.getLastName().isEmpty() || person.getLastName() == null) {
			editUserMessage+="Merci de saisir un nom de famille.";
			isValid = false;
		}
		// Vérifie que le mot de passe remplit les conditions nécessaires
		matcher = pattern.matcher(person.getUserPassword());
		if(!matcher.matches()) {
			validPasswordMessage ="Le mot de passe doit être composé d'au moins 8 caractères et contenir "
					+ "une majuscule, un chiffre et un caractère spécial parmi @#$%!?";
			isValid = false;
		}
		if(!isValid)
			return new ModelAndView("admins/editUser").addObject("createUserMessage", editUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Mise à jour de l'utilisateur et redirection
		personService.updatePerson(person);
		return new ModelAndView("redirect:/admin/userManagement");
	}
	
	/**
	 * Permet de réinitialiser le mot de passe d'un utilisateur 
	 * @param person L'utilisateur en question
	 * @return La redirection vers la gestion des utilisateurs
	 */
	@RequestMapping(value="/admin/reinitPassword", method=RequestMethod.GET)
	public String reinitPassword(Person person) {
		// Modification du mot de passe et mise à jour de l'utilisateur
		person.setUserPassword("sysmd7gd");
		personService.updatePerson(person);
		return "redirect:/admin/userManagement";
	}
	
	@RequestMapping(value="/admin/addGroup", method=RequestMethod.GET)
	public ModelAndView addGroup(HttpServletRequest request) {
		return new ModelAndView("/admins/addGroup");
	}
	
	@RequestMapping(value="/admin/addGroupProcess", method=RequestMethod.POST)
	public ModelAndView addGroupProcess(@ModelAttribute Group group) {
		// Message d'alerte si le groupe existe déja
		String groupAlreadyExistMessage = "";
		// Récupération de la liste des groupes pour vérification
		List<Group> listGroup = groupService.findAllGroup();
		for(Group g : listGroup) {
			if(g.getGroupName().equalsIgnoreCase(group.getGroupName())) {
				groupAlreadyExistMessage = "Le groupe existe déjà !!!";
				return new ModelAndView("/admins/addGroup").addObject("groupeAlreadyExistMessage", groupAlreadyExistMessage);
			}
		}
		// Création du groupe
		groupService.createGroup(group);
		return new ModelAndView("redirect:/admin/groupManagement");
	}
	
	@RequestMapping(value="/admin/deleteGroup")
	public ModelAndView deleteGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response) {	
		groupService.deleteGroup(groupService.findByPrimaryKey(request.getParameter("groupName")));
		return new ModelAndView("redirect:/admin/groupManagement");
	}
	
	/**
	 * Permet de créer la page d'ajout d'un utilisateur à un groupe et la création du-dit groupe s'il n'existe pas
	 * @param request La requête pour obtenir le nom du groupe
	 * @return La vue vers l'ajout d'un utlisateur à un groupe
	 */
	@RequestMapping(value="/admin/addUserToGroup", method=RequestMethod.POST)
	public ModelAndView addUserToGroup(HttpServletRequest request) {
		ModelAndView viewAddUSerToGroup = new ModelAndView("admins/addUserToGroup");
		// Récupération de la liste des personnes disponibles pour l'ajout au groupe
		List<Person> listPerson = personService.findAllPerson();
		//Récupération du groupe
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
			// Vérification : si des personnes appartiennent déjà au groupe, elle ne feront pas partie de la liste des personnes disponibles
		List<Person> listPersonCheck = new LinkedList<Person>(listPerson); // Création d'un seconde liste pour la vérification 
		for(Person person : listPersonCheck) {
			if(group.getGroupMembers().contains(person))
				listPerson.remove(person);
			}
		// Ajout des objets à la vue
		viewAddUSerToGroup.addObject("listPerson", listPerson);
		viewAddUSerToGroup.addObject("groupName", request.getParameter("groupName"));		
		return viewAddUSerToGroup;
	}
	
	/**
	 * Permet d'ajouter un utilisateur à un groupe
	 * @param request La requête pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/admin/addUserToGroupProcess", method=RequestMethod.GET)
	public ModelAndView addUserToGroupProcess(HttpServletRequest request) {
		// Récupération du groupe et de la personne à ajouter
		Person personToAdd = personService.findByPrimaryKey(request.getParameter("fullName"));
		Group groupToUpdate = groupService.findByPrimaryKey(request.getParameter("groupName"));		
		// Ajout de la personne au groupe et mise à jour du groupe
		groupToUpdate.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToUpdate);
		return new ModelAndView("redirect:/admin/groupManagement");
	}
	
	/**
	 * Permet la suppression d'un utilisateur d'un groupe
	 * @param request La requête pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/admin/removeUserFromGroup", method=RequestMethod.GET)
	public ModelAndView removeUserFromGroup(HttpServletRequest request) {
		//Test
		// Récupération de la personne à supprimer et du groupe concerné
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));
		// Si il n'y a qu'un seul membre dans le groupe, on empêche la suppression de la personne
		if(group.getGroupMembers().size() <= 1) {
			return new ModelAndView("redirect:/admin");
		}	
		// Suppression de la persone et mise à jour du groupe
		group.getGroupMembers().remove(person);
		groupService.updateGroup(group);
		// Redirection vers la gestion des groupes
		return new ModelAndView("redirect:/admin/groupManagement");
	}
	
	@RequestMapping(value="/admin/deleteUser", method=RequestMethod.GET)
	public ModelAndView deleteUser(Person person) {
		System.out.println(person.getFullName());
		List<Group> listGroup = groupService.findAllGroup();
		for(Group group : listGroup) {
			// Si c'est le dernier membre d'un groupe, on empêche la suppression, on affiche un message d'erreur et on quitte
			if(group.getGroupMembers().contains(person) && group.getGroupMembers().size() <= 1) {
				String onlyGroupMemberMessage = person.getFullName() +" est le seul membre du groupe " + group.getGroupName();
				return new ModelAndView("redirect:/admin/userManagement").addObject("onlyGroupMemberMessage", onlyGroupMemberMessage);
			}			
		}
		// Si on est arrivé ici, c'est que la personne à supprimer n'est l'unique membre d'aucun groupe
		for(Group group : listGroup) {
			if(group.getGroupMembers().contains(person)) {
				group.getGroupMembers().remove(person); // On retire la personne des groupes où elle est présente
				groupService.updateGroup(group);
			}
		}
		// Suppression de la personne
		personService.deletePerson(person);
		return new ModelAndView("redirect:/admin/userManagement");
	}
}
