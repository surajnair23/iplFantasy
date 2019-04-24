package com.ipl.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ipl.validator.UserValidator;

@Controller
@RequestMapping("signup.htm")
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	UserValidator uservalidator;
	
	@Autowired
	UserDao userdao;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(uservalidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
		
		logger.info("Routing to register");
		User user = new User();
		
		//command object
		model.addAttribute("user", user);
		//return form view
		return "register";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String successView(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap model) {
		boolean result = false;
		logger.info("handling form");
		if (bindingResult.hasErrors()) {
			return "register";
		}else {
			try {
				//explicitly trying to make the fields full
				user.setIsApproved(false);
				user.setIsAdmin(false);
				//formatting date for sql
				Date dt = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.format(dt);
				user.setCreatedDate(dt);
				user.setUdpatedDate(dt);
				//setting values to not null
				result = userdao.register(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//go to generic server issue page
//				return null; //add a 404 page
			}
			//re routing block
			if(result)
				return "redirect:login.htm";
			else {
				return null;
			}
		}
	}
}
