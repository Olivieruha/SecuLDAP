package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class UserController {

	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
	private Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	private Matcher matcher;
	
	@RequestMapping(value="/user")
	public ModelAndView homeUser(HttpServletResponse response, HttpServletRequest request) throws IOException{
		// Permet de r�cup�rer les informations d'identification
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String name = auth.getName();
		
		// R�cup�ration de la personne qui s'est identifi�e et insertion de celle-ci dans la vue 		
		return new ModelAndView("users/user").addObject("person", personService.findByPrimaryKey(name));
	}	
	
	@RequestMapping(value="/user/modifyPasswordForm", method=RequestMethod.POST)
	public ModelAndView checkAndChangePassword(@ModelAttribute Person person) {
		System.out.println(person.getFullName());
		System.out.println(person.getUserPassword());
		
		//TODO : Ecrire le message d'erreur pour le mot de passe
		matcher = pattern.matcher(person.getUserPassword());	
		if(matcher.matches()) {
			personService.updatePerson(person);
		} else {
			System.out.println("Mot de passe pas assez fort");
		}
		
		ModelAndView viewUser = new ModelAndView("users/user");
		viewUser.addObject("person", person);		
		return viewUser;

	}
}
