package com.ipl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ipl.dao.UserDao;

@Controller
public class MainPageController {
	
	@Autowired
	UserDao userdao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(MainPageController.class);
	private static final String pgntfn = "redirect:pageNtFound.htm";

	@RequestMapping(value="mainPage.htm", method = RequestMethod.GET)
	public String mainPage(HttpServletRequest request) {
		System.out.println("Routing to the mainpage");
		logger.info("Routing to main page");
		HttpSession session = request.getSession(false);
		if(session != null) {
			return "mainPage";
		}
		return pgntfn;
	}
	
//	approve user (show list)
	@RequestMapping(value="approval.htm", method = RequestMethod.GET)
	public String approvalList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			if(session.getAttribute("admin") != null) {
				return "approval";
			}else {
				return pgntfn;
			}
		}else {
			return pgntfn;
		}
	}
	
	//approve user
	@RequestMapping(value="userapprove.htm", method = RequestMethod.GET)
	public void approveUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String uid = request.getParameter("userid");
		long id = Long.parseLong(uid);
		if(session != null) {
			if(session.getAttribute("admin") != null) {
				userdao.approveUser(id);
			}
		}
	}
	
	//delete user
	@RequestMapping(value="userdelete.htm", method = RequestMethod.GET)
	public void deleteUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String uid = request.getParameter("userid");
		long id = Long.parseLong(uid);
		if(session != null) {
			if(session.getAttribute("admin") != null) {
				userdao.deleteUser(id);
			}
		}
	}
	
	//Sign out a User
	@RequestMapping(value="signout.htm",method = RequestMethod.GET)
	public String signout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:login.htm";
	}
	
	//Scoreboard update
	@RequestMapping(value="scoreboard.htm",method =RequestMethod.GET)
	public String updatePlayerscore(Model model, HttpServletRequest request) {
		
		//chck if the matchid in the url does has points, then just show that, else create form and update the details
		return "score";
	}
	
	//page not found
	@RequestMapping(value="pageNtFound.htm", method = RequestMethod.GET)
	public String pageNtFound(HttpServletRequest request) {
		return pgntfn;
	}
}
