package com.miage.SecuLDAP.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class HelpDeskController {

	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/helpdesk")
	public ModelAndView testSecure() throws IOException{
		ModelAndView viewHelpdesk = new ModelAndView("helpdesks/helpdesk");
		viewHelpdesk.addObject("listPerson", personService.findAllPerson());
		viewHelpdesk.addObject("listGroup", groupService.findAllGroup());
		return viewHelpdesk;
	}
	
	@RequestMapping(value="/helpdesk/deleteUser", method=RequestMethod.POST)
	public ModelAndView deleteUser(@ModelAttribute Person person) {
		ModelAndView viewHelpDesk = new ModelAndView("helpdesks/helpdesk");
		System.out.println(person.getFullName());
		personService.deletePerson(person);
		return viewHelpDesk;
	}
}
