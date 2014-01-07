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
public class GroupManagerController {
	
	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/groupmanager")
	public ModelAndView groupManager(HttpServletResponse response){
		return new ModelAndView("groupmanagers/groupmanager").addObject("groups", groupService.findAllGroup());
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
		// R�cup�ration de la personne � supprimer et du groupe concern�
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));
		// Si il n'y a qu'un seul membre dans le groupe, on emp�che la suppression de la personne
		if(group.getGroupMembers().size() <= 1) {
			return new ModelAndView("redirect:/groupmanager");
		}	
		// Suppression de la persone et mise � jour du groupe
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
		return new ModelAndView("redirect:/groupmanagers/addUserToGroup").addObject("groupName", groupToBeCreated.getGroupName());
	}
	
	/**
	 * Permet de cr�er la page d'ajout d'un utilisateur � un groupe
	 * @param request La requ�te pour obtenir le nom du groupe
	 * @return La vue vers l'ajout d'un utlisateur � un groupe
	 */
	@RequestMapping(value="/groupmanager/addUserToGroup", method=RequestMethod.GET)
	public ModelAndView addUserToGroup(HttpServletRequest request) {
		ModelAndView viewAddUSerToGroup = new ModelAndView("groupmanagers/addUserToGroup");
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
	@RequestMapping(value="/groupmanager/addUserToGroupProcess", method=RequestMethod.GET)
	public ModelAndView addUserToGroupProcess(HttpServletRequest request) {
		// R�cup�ration du groupe et de la personne � ajouter
		Person personToAdd = personService.findByPrimaryKey(request.getParameter("fullName"));
		Group groupToUpdate = groupService.findByPrimaryKey(request.getParameter("groupName"));		
		// Ajout de la personne au groupe et mise � jour du groupe
		groupToUpdate.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToUpdate);
		return new ModelAndView("redirect:/groupmanagers/groupManagement");
	}
	
	@RequestMapping(value="/groupmanager/removeGroup")
	public ModelAndView removeGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response) {	
		groupService.deleteGroup(groupService.findByPrimaryKey(request.getParameter("groupName")));
		return new ModelAndView("redirect:/groupmanager");
	}	
}
