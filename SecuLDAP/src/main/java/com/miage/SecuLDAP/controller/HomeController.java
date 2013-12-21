package com.miage.SecuLDAP.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.DAO.PersonDao;
import com.miage.SecuLDAP.DAO.PersonDaoImpl;
import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.PersonService;
import com.miage.SecuLDAP.service.PersonServiceImpl;

@Controller
public class HomeController {
	
	PersonServiceImpl psi = new PersonServiceImpl();
	PersonDao personDao = new PersonDaoImpl();

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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		System.out.println(name);
		personDao.setLdapTemplate(new LdapTemplate());
		 Person p = personDao.findByPrimaryKey(name);
		 if(p == null) System.out.println("person est null");
		 else System.out.println("person ok");
		
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
