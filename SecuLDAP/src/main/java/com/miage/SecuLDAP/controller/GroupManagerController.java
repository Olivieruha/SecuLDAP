package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.SecuLDAP.model.Group;
import com.miage.SecuLDAP.model.Person;
import com.miage.SecuLDAP.service.GroupService;
import com.miage.SecuLDAP.service.PersonService;

@Controller
public class GroupManagerController {
	
	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
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
		List<Group> listGroups = groupService.findAllGroup();

		// Création de la liste des membres (ce sont des objets de type Person) grâce au tableau des Dn (arrayDnMembers) contenu dans le groupe
		for(Group group : listGroups)
		{
			List<Person> groupMembers = new LinkedList<Person>();
			for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
				groupMembers.add(personService.findByDistinguishedName((group.getArrayDnMembers()[i])));
			group.setGroupMembers(groupMembers);
		}
		// Affichage dans la console
		/*
		System.out.println(group.getGroupName());	
		for(Person person : group.getGroupMembers())
			System.out.println(person.getFullName());*/
		/*ModelAndView view = new ModelAndView();
		view.addObject("groups",groups2);
		view.setViewName("groupsmanagers/groupmanager");
		return view;*/
		return new ModelAndView("groupmanagers/groupmanager").addObject("groups",listGroups);
		/**
		 * Test d'ajout d'un user à un groupe (permet de décrire la méthode d'ajout et donc, de suppression, de modification etc...
		 * Attention !!! Même remarque que pour la création d'un groupe : il est possible d'ajouter au groupe un membre qui n'existe pas dans l'AD !!!
		 * Il faut donc :
		 * - soit le ou les créer
		 * - soit obliger le créateur du groupe à selectionner un membre déjà existant dans l'AD
		 */
		/*Group groupToModify = groupService.findByPrimaryKey("admin");
		Person personToAdd = new Person();
		personToAdd.setFullName("laurent.erate");
		personToAdd.setLastName("erate");
		personToAdd.setUserPassword("secret");
		groupToModify.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToModify);
		// Affichage dans la console
		System.out.println(groupToModify.getGroupName());	
		for(Person person : groupToModify.getGroupMembers())
			System.out.println(person.getFullName());*/
		
		/**
		 * Test de la suppression d'un groupe
		 */
		//groupService.deleteGroup(groupService.findByPrimaryKey("test"));	
	}
	@RequestMapping(value="/groupmanager/adduser")
	public ModelAndView AddUser(HttpSession session, HttpServletRequest request, HttpServletResponse response, Person person) throws IOException{
		Person personToAdd = person;
		personToAdd.setUserPassword("secret");
		
		List<Group> listGroups = groupService.findAllGroup();
		// Création de la liste des membres (ce sont des objets de type Person) grâce au tableau des Dn (arrayDnMembers) contenu dans le groupe	
		for(Group group : listGroups)
		{
			List<Person> groupMembers = new LinkedList<Person>();
			for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
				groupMembers.add(personService.findByDistinguishedName((group.getArrayDnMembers()[i])));
			if(group == groupService.findByPrimaryKey(request.getParameter("groupName")))
			{
				groupMembers.add(personToAdd);
			}
			group.setGroupMembers(groupMembers);
		}
		personService.createPerson(personToAdd);
		
		/*Person personToAdd = person;
		personToAdd.setUserPassword("secret");
		personService.createPerson(personToAdd);*/
		/*
		Group groupToModify = groupService.findByPrimaryKey(request.getParameter("groupName"));
		System.out.println(groupToModify.getGroupName());
		groupToModify.getGroupMembers().add(personToAdd);
		groupService.updateGroup(groupToModify);*/
		
		//groupToModify.setGroupMembers(groupMembers);
		//groupService.updateGroup(groupToModify);
	
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/removeuser")
	public ModelAndView RemoveUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{	
		groupService.findByPrimaryKey(request.getParameter("groupName")).getGroupMembers().remove(personService.findByPrimaryKey(request.getParameter("fullName")));
		personService.deletePerson(personService.findByPrimaryKey(request.getParameter("fullName")));
		return new ModelAndView("redirect:/groupmanager");
	}
	
	@RequestMapping(value="/groupmanager/addgroup")
	public ModelAndView addgroup(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{	
		System.out.println(request.getParameter("groupName"));
		Group groupToBeCreated = new Group();
		groupToBeCreated.setGroupName(request.getParameter("groupName"));
		List<Person> groupMembers = new LinkedList<Person>();
		groupMembers.add(personService.findByPrimaryKey("jonathan.rubiero"));
		groupToBeCreated.setGroupMembers(groupMembers);
		groupService.createGroup(groupToBeCreated);
		return new ModelAndView("redirect:/groupmanager");
	}
	
}
