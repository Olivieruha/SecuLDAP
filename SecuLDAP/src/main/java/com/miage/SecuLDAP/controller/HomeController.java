package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.model.Group;
import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class HomeController {
	
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
	
	@RequestMapping(value="/helpdesk")
	public ModelAndView testSecure(HttpServletResponse response) throws IOException{		
		return new ModelAndView("helpdesk");
	}
	
	@RequestMapping(value="/groupmanager")
	public ModelAndView testExtrem(HttpServletResponse response) throws IOException{
		
		/**
		 * Test de la création d'un groupe
		 * Un groupe est composé de son nom et d'une liste de personnes
		 * Un groupe ne doit pas être vide (i.e avoir une liste de personne vide à la création) !!! => plantage + exception
		 * @TODO lorsque l'on crée un groupe  (avec un ou plusieurs membres), si le ou les membres n'existent pas, il faut :
		 * - soit le ou les créer
		 * - soit obliger le créateur du groupe à selectionner un membre déjà existant dans l'AD
		 */
		/*Group group = new Group();
		group.setGroupName("test");
		Person person = new Person();
		person.setFullName("mehdi.belage");
		person.setLastName("belage");
		person.setUserPassword("secret");
		List<Person> groupMembers = new LinkedList<Person>();
		groupMembers.add(person);
		group.setGroupMembers(groupMembers);
		groupService.createGroup(group);*/
		
		/**
		 * Test afin de retrouver un group dans l'AD et récupérer les personnes qui le compose
		 */
		/*Group group = groupService.findByPrimaryKey("admin");
		
		// Création de la liste des membres (ce sont des objets de type Person) grâce au tableau des Dn (arrayDnMembers) contenu dans le groupe
		List<Person> groupMembers = new LinkedList<Person>();
		for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
			groupMembers.add(personService.findByDistinguishedName((group.getArrayDnMembers()[i])));
		group.setGroupMembers(groupMembers);
		
		// Affichage dans la console
		System.out.println(group.getGroupName());	
		for(Person person : group.getGroupMembers())
			System.out.println(person.getFullName());
		*/
		/**
		 * Test d'ajout d'un user à un groupe (permet de décrire la méthode d'ajout et donc, de suppression, de modification etc...
		 * Attention !!! Même remarque que pour la création d'un groupe : il est possible d'ajouter au groupe un membre qui n'existe pas dans l'AD !!!
		 * Il faut donc :
		 * - soit le ou les créer
		 * - soit obliger le créateur du groupe à selectionner un membre déjà existant dans l'AD
		 */
		Group groupToModify = groupService.findByPrimaryKey("admin");
		Person personToAdd = new Person();
		personToAdd.setFullName("laurent.erate");
		personToAdd.setLastName("erate");
		personToAdd.setUserPassword("secret");
		groupToModify.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToModify);
		// Affichage dans la console
		System.out.println(groupToModify.getGroupName());	
		for(Person person : groupToModify.getGroupMembers())
			System.out.println(person.getFullName());
		
		return new ModelAndView("groupmanager");
	}
	
	@RequestMapping(value="/user")
	public ModelAndView testUser(HttpServletResponse response, HttpServletRequest request) throws IOException{
		// Permet de récupérer les informations d'identification
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		// Création de la personne récupérée dans l'annuaire LDAP
		// Pour récupérer d'autre information, voir le ContextMapper dans la DAO
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
