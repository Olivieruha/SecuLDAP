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

import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class HomeController {
	
	@Autowired
	PersonService personService;

	@RequestMapping(value="/admin")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("admin");
	}
	
	@RequestMapping(value="/helpdesk")
	public ModelAndView testSecure(HttpServletResponse response) throws IOException{
		return new ModelAndView("helpdesk");
	}
	
	@RequestMapping(value="/groupmanager")
	public ModelAndView testExtrem(HttpServletResponse response) throws IOException{
		return new ModelAndView("groupmanager");
	}
	
	@RequestMapping(value="/user")
	public ModelAndView testUser(HttpServletResponse response, HttpServletRequest request) throws IOException{
		// Permet de r�cup�rer les informations d'identification
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		// Cr�ation de la personne r�cup�r�e dans l'annuaire LDAP
		// Pour r�cup�rer d'autre information, voir le ContextMapper dans la DAO
		Person p = personService.findByPrimaryKey(name);		 
		System.out.println(p.getLastName());
		
		return new ModelAndView("user");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletResponse response) throws IOException{
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/redirection")
	public String redirectionAfterLogin(HttpServletRequest request) throws IOException{
		if(request.isUserInRole("ROLE_ADMIN")) return "redirect:/admin";
		if(request.isUserInRole("ROLE_HELPDESK")) return "redirect:/helpdesk";
		if(request.isUserInRole("ROLE_GROUPMANAGER")) return "redirect:/groupmanager";
		return "redirect:/user";			
	}
}
