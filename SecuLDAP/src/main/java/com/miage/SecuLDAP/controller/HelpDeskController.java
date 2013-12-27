package com.miage.SecuLDAP.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class HelpDeskController {

	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/helpdesk")
	public ModelAndView testSecure(HttpServletResponse response) throws IOException{
		ModelAndView viewHelpdesk = new ModelAndView("helpdesks/helpdesk");
		viewHelpdesk.addObject("listPerson", personService.findAllPerson());
		viewHelpdesk.addObject("listGroup", groupService.findAllGroup());
		return viewHelpdesk;
	}
	
}
