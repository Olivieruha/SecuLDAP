package com.miage.SecuLDAP.controller;

import java.util.LinkedList;
import java.util.List;

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
		List<Group> listGroup = groupService.findAllGroup();
		List<Group> listGroupCheck = new LinkedList<Group>(listGroup);
		for(Group group : listGroupCheck) {
			if(group.getGroupName().equalsIgnoreCase("admin") || group.getGroupName().equalsIgnoreCase("helpdesk") || group.getGroupName().equalsIgnoreCase("groupmanager")) {
				listGroup.remove(group);
			}
		}
		return new ModelAndView("groupmanagers/groupmanager").addObject("groups", listGroup);
	}	
	
	/**
	 * Permet la suppression d'un utilisateur d'un groupe
	 * @param request La requête pour obtenir le nom d'utilisateur et le nom du groupe
	 * @return La redirection vers la gestion des groupes
	 */
	@RequestMapping(value="/groupmanager/removeUserFromGroup", method=RequestMethod.GET)
	public ModelAndView removeUserFromGroup(HttpServletRequest request) {			
		// Récupération de la personne à supprimer et du groupe concerné
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));	
		// Suppression de la persone et mise à jour du groupe
		group.getGroupMembers().remove(person);
		groupService.updateGroup(group);
		// Redirection vers la gestion des groupes
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/addGroup", method=RequestMethod.GET)
	public ModelAndView addGroup(HttpServletRequest request) {
		return new ModelAndView("groupmanagers/addGroup");
	}
	
	@RequestMapping(value="/groupmanager/deleteGroup")
	public ModelAndView deleteGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response) {	
		groupService.deleteGroup(groupService.findByPrimaryKey(request.getParameter("groupName")));
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/addGroupProcess", method=RequestMethod.POST)
	public ModelAndView addGroupProcess(@ModelAttribute Group group) {
		// Message d'alerte si le groupe existe déja
		String groupAlreadyExistMessage = "";
		// Récupération de la liste des groupes pour vérification
		List<Group> listGroup = groupService.findAllGroup();
		for(Group g : listGroup) {
			if(g.getGroupName().equalsIgnoreCase(group.getGroupName())) {
				groupAlreadyExistMessage = "Le groupe existe déjà !!!";
				return new ModelAndView("/groupmanagers/addGroup").addObject("groupeAlreadyExistMessage", groupAlreadyExistMessage);
			}
		}
		// Création du groupe
		groupService.createGroup(group);
		return new ModelAndView("redirect:/groupmanager");
	}
	
	/**
	 * Permet de créer la page d'ajout d'un utilisateur à un groupe et la création du-dit groupe s'il n'existe pas
	 * @param request La requête pour obtenir le nom du groupe
	 * @return La vue vers l'ajout d'un utlisateur à un groupe
	 */
	@RequestMapping(value="/groupmanager/addUserToGroup", method=RequestMethod.GET)
	public ModelAndView addUserToGroup(HttpServletRequest request) {
		ModelAndView viewAddUSerToGroup = new ModelAndView("groupmanagers/addUserToGroup");
		// Récupération de la liste des personnes disponibles pour l'ajout au groupe
		List<Person> listPerson = personService.findAllPerson();
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
	@RequestMapping(value="/groupmanager/addUserToGroupProcess", method=RequestMethod.GET)
	public ModelAndView addUserToGroupProcess(HttpServletRequest request) {
		// Récupération du groupe et de la personne à ajouter
		Person personToAdd = personService.findByPrimaryKey(request.getParameter("fullName"));
		Group groupToUpdate = groupService.findByPrimaryKey(request.getParameter("groupName"));		
		// Ajout de la personne au groupe et mise à jour du groupe
		groupToUpdate.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToUpdate);	
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/removeGroup", method=RequestMethod.GET)
	public ModelAndView removeGroup(HttpServletRequest request) {	
		groupService.deleteGroup(groupService.findByPrimaryKey(request.getParameter("groupName")));
		return new ModelAndView("redirect:/groupmanager");
	}	
}
