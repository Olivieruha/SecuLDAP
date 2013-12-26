package com.miage.SecuLDAP.controller;

import java.io.IOException;
import java.util.LinkedList;
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
public class GroupManagerController {
	
	@Autowired
	PersonService personService;
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/groupmanager")
	public ModelAndView testExtrem(HttpServletResponse response) throws IOException{
		
		/**
		 * Test de la cr�ation d'un groupe
		 * Un groupe est compos� de son nom et d'une liste de personnes
		 * Un groupe ne doit pas �tre vide (i.e avoir une liste de personne vide � la cr�ation) !!! => plantage + exception
		 * @TODO lorsque l'on cr�e un groupe  (avec un ou plusieurs membres), si le ou les membres n'existent pas, il faut :
		 * - soit le ou les cr�er
		 * - soit obliger le cr�ateur du groupe � selectionner un membre d�j� existant dans l'AD
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
		 * Test afin de retrouver un group dans l'AD et r�cup�rer les personnes qui le compose
		 */
		/*Group group = groupService.findByPrimaryKey("admin");
		
		// Cr�ation de la liste des membres (ce sont des objets de type Person) gr�ce au tableau des Dn (arrayDnMembers) contenu dans le groupe
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
		 * Test d'ajout d'un user � un groupe (permet de d�crire la m�thode d'ajout et donc, de suppression, de modification etc...
		 * Attention !!! M�me remarque que pour la cr�ation d'un groupe : il est possible d'ajouter au groupe un membre qui n'existe pas dans l'AD !!!
		 * Il faut donc :
		 * - soit le ou les cr�er
		 * - soit obliger le cr�ateur du groupe � selectionner un membre d�j� existant dans l'AD
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
		
		return new ModelAndView("groupmanager");
	}
}
