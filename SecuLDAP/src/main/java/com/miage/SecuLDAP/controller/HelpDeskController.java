package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
public class HelpDeskController {

	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!?]).{8,20})";
	private Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	private Matcher matcher;
	
	@RequestMapping(value="/helpdesk/userManagement")
	public ModelAndView userManagement(@ModelAttribute Person person) throws IOException{
		ModelAndView viewUserManagement = new ModelAndView("helpdesks/userManagement");
		viewUserManagement.addObject("listPerson", personService.findAllPerson());
		viewUserManagement.addObject("person", person);
		return viewUserManagement;
	}
	
	@RequestMapping(value="/helpdesk/groupManagement")
	public ModelAndView groupManagement(@ModelAttribute Person person) throws IOException{
		ModelAndView viewGroupManagement = new ModelAndView("helpdesks/groupManagement");
		List<Group> listGroup = groupService.findAllGroup();
		for(Group group : listGroup) {
			List<Person> listPerson = new LinkedList<Person>();
			for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
				listPerson.add(personService.findByDistinguishedName(group.getArrayDnMembers()[i]));
			group.setGroupMembers(listPerson);
		}
		viewGroupManagement.addObject("listGroup", listGroup);
		return viewGroupManagement;
	}
	
	@RequestMapping(value="/helpdesk/addUser", method=RequestMethod.GET)
	public ModelAndView addUser(@ModelAttribute Person person) {
		ModelAndView viewAddUser = new ModelAndView("helpdesks/addUser");
		viewAddUser.addObject("person", person);
		return viewAddUser;
	}
	
	@RequestMapping(value="/helpdesk/addUserProcess", method=RequestMethod.POST)
	public ModelAndView addUserProcess(@ModelAttribute Person person) {
		String createUserMessage = "";
		String validPasswordMessage ="";
		boolean isValid = true;
		if(person.getFullName().isEmpty() || person.getFullName() == null) {
			createUserMessage+="Merci de saisir un nom d'utilisateur.\n";
			isValid = false;
		}
		if(person.getLastName().isEmpty() || person.getLastName() == null) {
			createUserMessage+="Merci de saisir un nom de famille.";
			isValid = false;
		}
		matcher = pattern.matcher(person.getUserPassword());
		if(!matcher.matches()) {
			validPasswordMessage ="Le mot de passe doit être composé d'au moins 8 caractères et contenir "
					+ "une majuscule, un chiffre et un caractère spécial parmi @#$%!?";
			isValid = false;
		}
		if(!isValid)
			return new ModelAndView("helpdesks/addUser").addObject("createUserMessage", createUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		
		personService.createPerson(person);
		return new ModelAndView("redirect:/helpdesk/userManagement");
	}
	
	@RequestMapping(value="/helpdesk/editUser", method=RequestMethod.GET)
	public ModelAndView editUser(@ModelAttribute Person person, HttpServletRequest request) {
		ModelAndView viewEditUser = new ModelAndView("helpdesks/editUser");
		viewEditUser.addObject("person", person);
		return viewEditUser;
	}
	
	@RequestMapping(value="/helpdesk/editUserProcess", method=RequestMethod.POST)
	public ModelAndView editUserProcess(@ModelAttribute Person person) {
		String editUserMessage = "";
		String validPasswordMessage ="";
		boolean isValid = true;
		if(person.getFullName().isEmpty() || person.getFullName() == null) {
			editUserMessage+="Merci de saisir un nom d'utilisateur.\n";
			isValid = false;
		}
		if(person.getLastName().isEmpty() || person.getLastName() == null) {
			editUserMessage+="Merci de saisir un nom de famille.";
			isValid = false;
		}
		matcher = pattern.matcher(person.getUserPassword());
		if(!matcher.matches()) {
			validPasswordMessage ="Le mot de passe doit être composé d'au moins 8 caractères et contenir "
					+ "une majuscule, un chiffre et un caractère spécial parmi @#$%!?";
			isValid = false;
		}
		if(!isValid)
			return new ModelAndView("helpdesks/editUser").addObject("createUserMessage", editUserMessage).addObject("validPasswordMessage", validPasswordMessage);
		
		personService.updatePerson(person);
		return new ModelAndView("redirect:/helpdesk/userManagement");
	}
	
	@RequestMapping(value="/helpdesk/deleteUser", method=RequestMethod.GET)
	public String deleteUser(Person person) {
		personService.deletePerson(person);
		return "redirect:/helpdesk/userManagement";
	}
	
	@RequestMapping(value="/helpdesk/reinitPassword", method=RequestMethod.GET)
	public String reinitPassword(Person person) {
		person.setUserPassword("sysmd7gd");
		personService.updatePerson(person);
		return "redirect:/helpdesk/userManagement";
	}
	
	@RequestMapping(value="/helpdesk/removeUserFromGroup", method=RequestMethod.GET)
	public ModelAndView removeUserFromGroup(HttpServletRequest request) {
		Group group = groupService.findByPrimaryKey(request.getParameter("groupName"));
		Person person = personService.findByPrimaryKey(request.getParameter("fullName"));
		
		// Si il n'y a qu'un seul membre dans le groupe, on empêche sa suppression
		if(group.getGroupMembers().size() <= 1) {
			return new ModelAndView("redirect:/helpdesk/groupManagement");
		}
		// Vérification juste au cas ou...
		if(group.getGroupMembers().contains(person)) {
			group.getGroupMembers().remove(person);
			groupService.updateGroup(group);
		} 
		return new ModelAndView("redirect:/helpdesk/groupManagement");
	}
}
