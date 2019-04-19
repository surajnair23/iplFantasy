package com.ipl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ipl.dao.UserDao;
import com.ipl.pojo.User;
import com.ipl.validator.LoginValidator;

@Controller
@RequestMapping(value="login.htm")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginValidator loginvalidator;
	
	@Autowired
	UserDao userdao;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(loginvalidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request){
		
		User user = new User();
		//command object
		model.addAttribute(user);
		
		String page = null;
		
		logger.info("Routing to login");
		//if a session exists already, just take him to requried page
		HttpSession sessionExists = request.getSession(false);
		if(sessionExists != null) {
			if(sessionExists.getAttribute("admin") != null) {
				//redirecting to mainpage as an admin
				page = "redirect:mainPage.htm";
			}
			if(sessionExists.getAttribute("user") != null) {
				//redirecting to mainpage as user
				page = "redirect:mainPage.htm";
			}
		}else {
			page = "login";
		}
		return page;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String successView(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap model,HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		logger.info("handling form");
		if (bindingResult.hasErrors()) {
			return "login";  //the are validation errors, go to the form view
		}else {
			Boolean isAdmin = userdao.isAdmin(user);
			if(isAdmin) {
				session.setAttribute("admin", user);
			}else {
				session.setAttribute("user", user);
			}
			return "redirect:mainPage.htm";
		}
	}
}
