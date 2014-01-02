package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@RequestMapping(value="/admin")
	public ModelAndView admin(HttpServletResponse response) {
		List<Group> listGroups = groupService.findAllGroup();

		// Création de la liste des membres (ce sont des objets de type Person) grâce au tableau des Dn (arrayDnMembers) contenu dans le groupe
		for(Group group : listGroups)
		{
			List<Person> groupMembers = new LinkedList<Person>();
			for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
				groupMembers.add(personService.findByDistinguishedName((group.getArrayDnMembers()[i])));
			group.setGroupMembers(groupMembers);
		}
		return new ModelAndView("admins/admin").addObject("groups",listGroups);
	}
	
	@RequestMapping(value="/admin/removeUserFromGroup")
	public ModelAndView removeUserFromGroup(HttpServletResponse response) 
	{
		return new ModelAndView("redirect:/admin");
	}
	
	@RequestMapping(value="/admin/deleterUser")
	public ModelAndView deleteUser(HttpServletResponse response) 
	{
		return new ModelAndView("redirect:/admin");
	}
	
	@RequestMapping(value="/admin/addGroup", method=RequestMethod.GET)
	public ModelAndView addGroup(HttpServletRequest request) {
		System.out.println("ok");
		return new ModelAndView("admin/addGroup");
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
}
