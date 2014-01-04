package com.miage.SecuLDAP.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
public class HelpDeskController {

	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!?]).{8,20})";
	private Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	private Matcher matcher;
	
	/**
	 * Permet de contruire la vue de gestion des personnes
	 * @return La vue de gestion des utilisateurs
	 */
	@RequestMapping(value="/helpdesk/userManagement")
	public ModelAndView userManagement() {
		return new ModelAndView("helpdesks/userManagement").addObject("listPerson", personService.findAllPerson());
	}
	
	/**
	 * Permet de constuire la vue de gestion des groupes
	 * @return La vue de gestion des groupes
	 */
	@RequestMapping(value="/helpdesk/groupManagement")
	public ModelAndView groupManagement() {
		// Affiche uniquement les groupes visibles pour le helpdesk
		List<Group> listGroup = groupService.findAllGroup();
		List<Group> listGroupCheck = new LinkedList<Group>(listGroup);
		for(Group group : listGroupCheck) {
			if(group.getGroupName().equals("admin") || group.getGroupName().equals("helpdesk") || group.getGroupName().equals("groupmanager")) {
				listGroup.remove(group);
			}
		}
		return new ModelAndView("helpdesks/groupManagement").addObject("listGroup", listGroup);
	}
	
	/**
	 * Permet de constuire la page de cr�ation d'un utilisateur
	 * @param person L'utilisateur � cr�er
	 * @return La vue de cr�ation d'un utilisateur
	 */
	@RequestMapping(value="/helpdesk/addUser", method=RequestMethod.GET)
	public ModelAndView addUser(Person person) {
		return new ModelAndView("helpdesks/addUser").addObject("person", person);
	}
	
	/**
	 * Permet de cr�er un utilisateur
	 * @param person L'utilisateur � cr�er
	 * @return Si cr�ation valide, la redirection vers la gestion des utilisateurs sinon la vue de cr�ation de l'utilisateur
	 */
	@RequestMapping(value="/helpdesk/addUserProcess", method=RequestMethod.POST)
	public ModelAndView addUserProcess(@ModelAttribute Person person) {
		// Messages d'alertes si un des champs est mal renseign�
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
		// V�rifie que le mot de passe remplit les conditions n�cessaires
		matcher = pattern.matcher(person.getUserPassword());
		if(!matcher.matches()) {
			validPasswordMessage ="Le mot de passe doit �tre compos� d'au moins 8 caract�res et contenir "
					+ "une majuscule, un chiffre et un caract�re sp�cial parmi @#$%!?";
			isValid = false;
		}
		if(!isValid)
			return new ModelAndView("helpdesks/addUser").addObject("createUserMessage", createUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Cr�ation de l'utilisateur et redirection
		personService.createPerson(person);
		return new ModelAndView("redirect:/helpdesk/userManagement");
	}
	
	/**
	 * Permet de contruire la page de modification d'un utilisateur
	 * @param person L'utilisateur � modifier
	 * @return La vue d'�dition de l'utilisateur
	 */
	@RequestMapping(value="/helpdesk/editUser", method=RequestMethod.GET)
	public ModelAndView editUser(Person person) {
		return new ModelAndView("helpdesks/editUser").addObject("person", person);
	}
	
	/**
	 * Permet de modifier un utilisateur
	 * @param person L'utilisateur � modifier
	 * @return Si modification valide, la redirection vers la gestion des utilisateurs sinon la vue d'�dition de l'utilisateur
	 */
	@RequestMapping(value="/helpdesk/editUserProcess", method=RequestMethod.POST)
	public ModelAndView editUserProcess(@ModelAttribute Person person) {
		// Messages d'alertes si un des champs est mal renseign�
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
		// V�rifie que le mot de passe remplit les conditions n�cessaires
		matcher = pattern.matcher(person.getUserPassword());
		if(!matcher.matches()) {
			validPasswordMessage ="Le mot de passe doit �tre compos� d'au moins 8 caract�res et contenir "
					+ "une majuscule, un chiffre et un caract�re sp�cial parmi @#$%!?";
			isValid = false;
		}
		if(!isValid)
			return new ModelAndView("helpdesks/editUser").addObject("createUserMessage", editUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Mise � jour de l'utilisateur et redirection
		personService.updatePerson(person);
		return new ModelAndView("redirect:/helpdesk/userManagement");
	}
	
	/**
	 * Permet du supprimer un utilisateur de l'annuaire (le supprime �galement de tous les groupes dans lesquelles il est pr�sent)
	 * @param person L'utilisateur en question
	 * @return La redirection vers la gestion des utilisateurs
	 */
	@RequestMapping(value="/helpdesk/deleteUser", method=RequestMethod.GET)
	public ModelAndView deleteUser(Person person) {
		List<Group> listGroup = groupService.findAllGroup();
		for(Group group : listGroup) {
			// Si c'est le dernier membre d'un groupe, on emp�che la suppression, on affiche un message d'erreur et on quitte
			if(group.getGroupMembers().contains(person) && group.getGroupMembers().size() <= 1) {
				String onlyGroupMemberMessage = person.getFullName() +" est le seul membre du groupe " + group.getGroupName();
				return new ModelAndView("redirect:/helpdesk/userManagement").addObject("onlyGroupMemberMessage", onlyGroupMemberMessage);
			}			
		}
		// Si on est arriv� ici, c'est que la personne � supprimer n'est l'unique membre d'aucun groupe
		for(Group group : listGroup) {
			if(group.getGroupMembers().contains(person)) {
				group.getGroupMembers().remove(person); // On retire la personne des groupes o� elle est pr�sente
				groupService.updateGroup(group);
			}
		}
		// Suppression de la personne
		personService.deletePerson(person);
		return new ModelAndView("redirect:/helpdesk/userManagement");
	}
	
	/**
	 * Permet de r�initialiser le mot de passe d'un utilisateur 
	 * @param person L'utilisateur en question
	 * @return La redirection vers la gestion des utilisateurs
	 */
	@RequestMapping(value="/helpdesk/reinitPassword", method=RequestMethod.GET)
	public String reinitPassword(Person person) {
		// Modification du mot de passe et mise � jour de l'utilisateur
		person.setUserPassword("sysmd7gd");
		personService.updatePerson(person);
		return "redirect:/helpdesk/userManagement";
	}
	
	/**
	 * Permet de cr�er la page d'ajout d'un utilisateur � un groupe
	 * @param request La requ�te pour obtenir le nom du groupe
	 * @return La vue vers l'ajout d'un utlisateur � un groupe
	 */
	@RequestMapping(value="/helpdesk/addUserToGroup", method=RequestMethod.GET)
	public ModelAndView addUserToGroup(HttpServletRequest request) {
		ModelAndView viewAddUSerToGroup = new ModelAndView("helpdesks/addUserToGroup");
		// R�cup�ration de la liste des personnes disponibles pour l'ajout au groupe
		List<Person> listPerson = personService.findAllPerson();
		//R�cup�ration du groupe
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		// V�rification : si des personnes appartiennent d�j� au groupe, elle ne feront pas partie de la liste des personnes disponibles
		List<Person> listPersonCheck = new LinkedList<Person>(listPerson); // Cr�ation d'un seconde liste pour la v�rification 
		for(Person person : listPersonCheck) {
			if(group.getGroupMembers().contains(person))
				listPerson.remove(person);
		}
		// Ajout des objets � la vue
		viewAddUSerToGroup.addObject("listPerson", listPerson);
		viewAddUSerToGroup.addObject("groupName", group.getGroupName());		
		return viewAddUSerToGroup;
	}
	
	/**
	 * Permet d'ajouter un utilisateur � un groupe
	 * @param request La requ�te pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/helpdesk/addUserToGroupProcess", method=RequestMethod.GET)
	public ModelAndView addUserToGroupProcess(HttpServletRequest request) {
		// R�cup�ration du groupe et de la personne � ajouter
		Person personToAdd = personService.findByPrimaryKey(request.getParameter("fullName"));
		Group groupToUpdate = groupService.findByPrimaryKey(request.getParameter("groupName"));		
		// Ajout de la personne au groupe et mise � jour du groupe
		groupToUpdate.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToUpdate);
		return new ModelAndView("redirect:/helpdesk/groupManagement");
	}
	
	/**
	 * Permet la suppression d'un utilisateur d'un groupe
	 * @param request La requ�te pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/helpdesk/removeUserFromGroup", method=RequestMethod.GET)
	public ModelAndView removeUserFromGroup(HttpServletRequest request) {
		// R�cup�ration de la personne � supprimer et du groupe concern�
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));
		// Si il n'y a qu'un seul membre dans le groupe, on emp�che la suppression de la personne
		if(group.getGroupMembers().size() <= 1) {
			return new ModelAndView("redirect:/helpdesk/groupManagement");
		}	
		// Suppression de la persone et mise � jour du groupe
		group.getGroupMembers().remove(person);
		groupService.updateGroup(group);
		// Redirection vers la gestion des groupes
		return new ModelAndView("redirect:/helpdesk/groupManagement");
	}
}
