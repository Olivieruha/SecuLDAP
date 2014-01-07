package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
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
public class GroupManagerController {
	
	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/groupmanager")
	public ModelAndView groupManager(HttpServletResponse response){
		return new ModelAndView("groupmanagers/groupmanager").addObject("groups", groupService.findAllGroup());
	}
	
	@RequestMapping(value="/groupmanager/adduser")
	public ModelAndView addUser(HttpSession session, HttpServletRequest request, HttpServletResponse response, Person person) throws IOException{
		Person personToBeCreated = person;
		personToBeCreated.setUserPassword("sysmd7gd");
		System.out.println(personToBeCreated.getFullName());
		
		try {
			if(!person.getFullName().equals(personService.findByPrimaryKey(person.getFullName())));
		} catch(NameNotFoundException nNFE) {
			personService.createPerson(personToBeCreated);
		}
		
		Group groupToBeUpdated = groupService.findByPrimaryKey(request.getParameter("groupName"));
		List<Person> groupMembers = new LinkedList<Person>();
		for(int i = 0 ; i < groupToBeUpdated.getArrayDnMembers().length ; ++i)
			groupMembers.add(personService.findByDistinguishedName((groupToBeUpdated.getArrayDnMembers()[i])));
		groupMembers.add(personToBeCreated);
		groupToBeUpdated.setGroupMembers(groupMembers);

		for(Person p : groupToBeUpdated.getGroupMembers()) {
			System.out.println(p.getFullName());
		}
		groupService.updateGroup(groupToBeUpdated);	
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/removeuser", method=RequestMethod.GET)
	public ModelAndView removeUser(HttpServletRequest request) {			
		/*Group groupToBeUpdated = groupService.findByPrimaryKey(request.getParameter("groupName"));
		List<Person> groupMembers = new LinkedList<Person>();
		for(int i = 0 ; i < groupToBeUpdated.getArrayDnMembers().length ; ++i)
		{			
			if(!personService.findByDistinguishedName(groupToBeUpdated.getArrayDnMembers()[i]).getFullName().equals(request.getParameter("fullName")))
				groupMembers.add(personService.findByDistinguishedName(groupToBeUpdated.getArrayDnMembers()[i]));
		}	
		groupToBeUpdated.setGroupMembers(groupMembers);
		groupService.updateGroup(groupToBeUpdated);	*/
		// Récupération de la personne à supprimer et du groupe concerné
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));
		// Si il n'y a qu'un seul membre dans le groupe, on empêche la suppression de la personne
		if(group.getGroupMembers().size() <= 1) {
			return new ModelAndView("redirect:/groupmanager");
		}	
		// Suppression de la persone et mise à jour du groupe
		group.getGroupMembers().remove(person);
		groupService.updateGroup(group);
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/addGroup", method=RequestMethod.GET)
	public ModelAndView addGroup(HttpServletRequest request) {
		return new ModelAndView("groupmanagers/addGroup");
	}
	
	@RequestMapping(value="/groupmanager/addGroupProcess")
	public ModelAndView addGroupProcess(HttpServletRequest request) {	
		Group groupToBeCreated = new Group();
		groupToBeCreated.setGroupName(request.getParameter("groupName"));
		groupService.createGroup(groupToBeCreated);
		return new ModelAndView("redirect:/groupmanager/addUserToGroup").addObject("groupName", groupToBeCreated.getGroupName());
	}
	
	/**
	 * Permet de créer la page d'ajout d'un utilisateur à un groupe
	 * @param request La requête pour obtenir le nom du groupe
	 * @return La vue vers l'ajout d'un utlisateur à un groupe
	 */
	@RequestMapping(value="/groupManager/addUserToGroup", method=RequestMethod.GET)
	public ModelAndView addUserToGroup(HttpServletRequest request) {
		ModelAndView viewAddUSerToGroup = new ModelAndView("groupmanagers/addUserToGroup");
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
		viewAddUSerToGroup.addObject("groupName", group.getGroupName());		
		return viewAddUSerToGroup;
	}
	
	/**
	 * Permet d'ajouter un utilisateur à un groupe
	 * @param request La requête pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/groupeManager/addUserToGroupProcess", method=RequestMethod.GET)
	public ModelAndView addUserToGroupProcess(HttpServletRequest request) {
		// Récupération du groupe et de la personne à ajouter
		Person personToAdd = personService.findByPrimaryKey(request.getParameter("fullName"));
		Group groupToUpdate = groupService.findByPrimaryKey(request.getParameter("groupName"));		
		// Ajout de la personne au groupe et mise à jour du groupe
		groupToUpdate.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToUpdate);
		return new ModelAndView("redirect:/helpdesk/groupManagement");
	}
	
	@RequestMapping(value="/groupmanager/removegroup")
	public ModelAndView removeGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response) {	
		groupService.deleteGroup(groupService.findByPrimaryKey(request.getParameter("groupName")));
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/edituser", method=RequestMethod.GET)
	public ModelAndView editUser(Person person) {
		return new ModelAndView("helpdesks/editUser").addObject("person", person);
	}
	
	@RequestMapping(value="/groupmanager/editUserProcess", method=RequestMethod.POST)
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
		if(!isValid)
			return new ModelAndView("helpdesks/editUser").addObject("createUserMessage", editUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		// Mise à jour de l'utilisateur et redirection
		personService.updatePerson(person);
		return new ModelAndView("redirect:/groupmanager/");
	}
	
	
}
