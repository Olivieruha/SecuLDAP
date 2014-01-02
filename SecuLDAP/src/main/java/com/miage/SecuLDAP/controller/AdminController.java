package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	

	@RequestMapping(value="/admin")
	public ModelAndView admin() {
		return new ModelAndView("admins/admin").addObject("groups", groupService.findAllGroup());
	}
	
	/**
	 * Permet de constuire la page de cr�ation d'un utilisateur
	 * @param person L'utilisateur � cr�er
	 * @return La vue de cr�ation d'un utilisateur
	 */
	@RequestMapping(value="/admin/addUser", method=RequestMethod.GET)
	public ModelAndView addUser(Person person) {
		return new ModelAndView("admins/addUser").addObject("person", person);
	}
	
	/**
	 * Permet de cr�er un utilisateur
	 * @param person L'utilisateur � cr�er
	 * @return Si cr�ation valide, la redirection vers la gestion des utilisateurs sinon la vue de cr�ation de l'utilisateur
	 */
	@RequestMapping(value="/admin/addUserProcess", method=RequestMethod.POST)
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
			return new ModelAndView("admins/addUser").addObject("createUserMessage", createUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Cr�ation de l'utilisateur et redirection
		personService.createPerson(person);
		return new ModelAndView("redirect:/admin");
	}
	

	
	@RequestMapping(value="/admin/addGroup", method=RequestMethod.GET)
	public ModelAndView addGroup(HttpServletRequest request) {
		return new ModelAndView("admins/addGroup");
	}
	
	@RequestMapping(value="/admin/addGroupProcess")
	public ModelAndView addGroupProcess(HttpServletRequest request) {	
		Group groupToBeCreated = new Group();
		groupToBeCreated.setGroupName(request.getParameter("groupName"));
		List<Person> groupMembers = new LinkedList<Person>();
		groupMembers.add(personService.findByPrimaryKey("jonathan.rubiero"));
		groupToBeCreated.setGroupMembers(groupMembers);
		groupService.createGroup(groupToBeCreated);
		return new ModelAndView("redirect:/admin");
	}
	
	
	/**
	 * Permet de contruire la page de modification d'un utilisateur
	 * @param person L'utilisateur � modifier
	 * @return La vue d'�dition de l'utilisateur
	 */
	@RequestMapping(value="/admin/editUser", method=RequestMethod.GET)
	public ModelAndView editUser(Person person) {
		return new ModelAndView("admin/editUser").addObject("person", person);
	}
		
	/**
	 * Permet de modifier un utilisateur
	 * @param person L'utilisateur � modifier
	 * @return Si modification valide, la redirection vers la gestion des utilisateurs sinon la vue d'�dition de l'utilisateur
	 */
	@RequestMapping(value="/admin/editUserProcess", method=RequestMethod.POST)
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
			return new ModelAndView("admin/editUser").addObject("createUserMessage", editUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Mise � jour de l'utilisateur et redirection
		personService.updatePerson(person);
		return new ModelAndView("redirect:/admin");
	}
	
	
	/**
	 * Permet de r�initialiser le mot de passe d'un utilisateur 
	 * @param person L'utilisateur en question
	 * @return La redirection vers la gestion des utilisateurs
	 */
	@RequestMapping(value="/admin/reinitPassword", method=RequestMethod.GET)
	public String reinitPassword(Person person) {
		// Modification du mot de passe et mise � jour de l'utilisateur
		person.setUserPassword("sysmd7gd");
		personService.updatePerson(person);
		return "redirect:/admin";
	}
	
	/**
	 * Permet de cr�er la page d'ajout d'un utilisateur � un groupe
	 * @param request La requ�te pour obtenir le nom du groupe
	 * @return La vue vers l'ajout d'un utlisateur � un groupe
	 */
	@RequestMapping(value="/admin/addUserToGroup", method=RequestMethod.GET)
	public ModelAndView addUserToGroup(HttpServletRequest request) {
		ModelAndView viewAddUSerToGroup = new ModelAndView("admins/addUserToGroup");
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
	@RequestMapping(value="/admin/addUserToGroupProcess", method=RequestMethod.GET)
	public ModelAndView addUserToGroupProcess(HttpServletRequest request) {
		// R�cup�ration du groupe et de la personne � ajouter
		Person personToAdd = personService.findByPrimaryKey(request.getParameter("fullName"));
		Group groupToUpdate = groupService.findByPrimaryKey(request.getParameter("groupName"));		
		// Ajout de la personne au groupe et mise � jour du groupe
		groupToUpdate.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToUpdate);
		return new ModelAndView("redirect:/admin");
	}
	
	/**
	 * Permet la suppression d'un utilisateur d'un groupe
	 * @param request La requ�te pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/admin/removeUserFromGroup", method=RequestMethod.GET)
	public ModelAndView removeUserFromGroup(HttpServletRequest request) {
		// R�cup�ration de la personne � supprimer et du groupe concern�
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));
		// Si il n'y a qu'un seul membre dans le groupe, on emp�che la suppression de la personne
		if(group.getGroupMembers().size() <= 1) {
			return new ModelAndView("redirect:/admin");
		}	
		// Suppression de la persone et mise � jour du groupe
		group.getGroupMembers().remove(person);
		groupService.updateGroup(group);
		// Redirection vers la gestion des groupes
		return new ModelAndView("redirect:/admin");
	}
	
	@RequestMapping(value="/admin/deleterUser")
	public ModelAndView deleteUser(HttpServletResponse response) 
	{
		return new ModelAndView("redirect:/admin");
	}
	
	
}
