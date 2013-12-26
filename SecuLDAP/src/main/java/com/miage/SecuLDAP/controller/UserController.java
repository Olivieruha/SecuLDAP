package com.miage.SecuLDAP.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class UserController {

	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/user")
	public ModelAndView testUser(HttpServletResponse response, HttpServletRequest request) throws IOException{
		// Permet de récupérer les informations d'identification
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		// Création de la personne récupérée dans l'annuaire LDAP
		// Pour récupérer d'autre information, voir le ContextMapper dans la DAO	 	
		ModelAndView viewUser = new ModelAndView("user");
		viewUser.addObject("person", personService.findByPrimaryKey(name));
		
		return viewUser;
	}
}
