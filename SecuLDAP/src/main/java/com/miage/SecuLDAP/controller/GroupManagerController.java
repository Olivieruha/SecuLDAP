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
	public ModelAndView testExtrem(HttpServletResponse response){
		List<Group> listGroups = groupService.findAllGroup();

		// Création de la liste des membres (ce sont des objets de type Person) grâce au tableau des Dn (arrayDnMembers) contenu dans le groupe
		for(Group group : listGroups)
		{
			List<Person> groupMembers = new LinkedList<Person>();
			for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
				groupMembers.add(personService.findByDistinguishedName((group.getArrayDnMembers()[i])));
			group.setGroupMembers(groupMembers);
		}
		return new ModelAndView("groupmanagers/groupmanager").addObject("groups",listGroups);
	}
	
	@RequestMapping(value="/groupmanager/adduser")
	public ModelAndView adduser(HttpSession session, HttpServletRequest request, HttpServletResponse response, Person person) throws IOException{
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
	public ModelAndView removeuser(HttpServletRequest request) {			
		Group groupToBeUpdated = groupService.findByPrimaryKey(request.getParameter("groupName"));
		List<Person> groupMembers = new LinkedList<Person>();
		for(int i = 0 ; i < groupToBeUpdated.getArrayDnMembers().length ; ++i)
		{			
			if(!personService.findByDistinguishedName(groupToBeUpdated.getArrayDnMembers()[i]).getFullName().equals(request.getParameter("fullName")))
				groupMembers.add(personService.findByDistinguishedName(groupToBeUpdated.getArrayDnMembers()[i]));
		}	
		groupToBeUpdated.setGroupMembers(groupMembers);
		groupService.updateGroup(groupToBeUpdated);	
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/addGroup", method=RequestMethod.GET)
	public ModelAndView addgroup(HttpServletRequest request) {	
		return new ModelAndView("redirect:/addGroup");
	}
	
	@RequestMapping(value="/groupmanager/addGroupProcess", method=RequestMethod.GET)
	public ModelAndView addGroupProcess(HttpServletRequest request) {	
		Group groupToBeCreated = new Group();
		groupToBeCreated.setGroupName(request.getParameter("groupName"));
		List<Person> groupMembers = new LinkedList<Person>();
		groupMembers.add(personService.findByPrimaryKey("jonathan.rubiero"));
		groupToBeCreated.setGroupMembers(groupMembers);
		groupService.createGroup(groupToBeCreated);
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/removegroup")
	public ModelAndView removegroup(HttpSession session, HttpServletRequest request, HttpServletResponse response) {	
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
		//return new ModelAndView("redirect:/groupmanager");
	}
	
	
}
