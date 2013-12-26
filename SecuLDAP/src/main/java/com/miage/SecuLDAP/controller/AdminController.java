package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ModelAndView test(HttpServletResponse response) throws IOException{
		
		List<Person> listPerson = personService.findAllPerson();
		for(Person p : listPerson) System.out.println(p.getFullName());
		
		List<Group> listGroup = groupService.findAllGroup();
		for(Group g : listGroup) System.out.println(g.getGroupName());
		
		return new ModelAndView("admin");
	}
}
