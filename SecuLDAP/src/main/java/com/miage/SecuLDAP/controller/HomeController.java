package com.miage.SecuLDAP.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/secure")
	public ModelAndView testSecure(HttpServletResponse response) throws IOException{
		return new ModelAndView("secure");
	}
	
	@RequestMapping(value="/secure/extrem")
	public ModelAndView testExtrem(HttpServletResponse response) throws IOException{
		return new ModelAndView("extrem");
	}
}
