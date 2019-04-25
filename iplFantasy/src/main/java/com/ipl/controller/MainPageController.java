package com.ipl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ipl.dao.UserDao;
import com.ipl.pojo.User;
import com.ipl.utility.SendEmail;

//*---------------|--------------------|--------------*//
//mainPage.htm	  | mainpage.jsp	   | landing page   
//approval.htm	  | approval		   | authorize users
//userapprove.htm | void			   | AJAx call
//userdelete.htm  | void		   	   | AJAx call
//scoreboard.htm  | score.jsp   	   | dash board to update player scores
//signout.htm     | redirect:login.htm | sign out page    

@Controller
public class MainPageController {
	
	@Autowired
	UserDao userdao;
	
	@Autowired
	SendEmail sendemail;
	
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
	
	@RequestMapping(value="userapprove.htm", method = RequestMethod.GET)
	public void approveUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String uid = request.getParameter("userid");
		long id = Long.parseLong(uid);
		if(session != null) {
			if(session.getAttribute("admin") != null) {
				if(userdao.approveUser(id)) {
					//send email that user has been authorized
					User userapproved = userdao.getLoggedUser(id);
					sendemail.userApprovedEmail(userapproved);
				}
			}
		}
	}
	
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
	
	@RequestMapping(value="signout.htm",method = RequestMethod.GET)
	public String signout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:login.htm";
	}
	
	//page not found
	@RequestMapping(value="pageNtFound.htm", method = RequestMethod.GET)
	public String pageNtFound(HttpServletRequest request) {
		return pgntfn;
	}
}
