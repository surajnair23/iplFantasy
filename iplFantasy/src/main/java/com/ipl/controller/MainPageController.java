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
	@RequestMapping(value="userapprove.htm", method = RequestMethod.POST)
	public String approveUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String reqIds[] = request.getParameterValues("userIds");
		long id;
		boolean isApproved = false;
		if(session != null) {
			if(session.getAttribute("admin") != null) {
				if(reqIds == null || reqIds.length <= 0) {
					return "approval";
				}else {
					for(String s: reqIds){
						id = Long.parseLong(s);
						isApproved = userdao.approveUser(id);
						if(!isApproved) {
							break;
						}
					}
				}
				return "approval";
			}
		return pgntfn;
		}else {
			return pgntfn;
		}
	}
	
	//delete user
	@RequestMapping(value="userdelete.htm", method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String reqIds[] = request.getParameterValues("userIds");
		long id;
		boolean isDeleted = false;
		if(session != null) {
			if(session.getAttribute("admin") != null) {
				if(reqIds == null || reqIds.length <= 0) {
					return "approval";
				}else {
					for(String s: reqIds){
						id = Long.parseLong(s);
						isDeleted = userdao.deleteUser(id);
						if(!isDeleted) {
							break;
						}
					}
				}	
				return "redirect:approval.htm";
			}
			return pgntfn;
		}else {
			return pgntfn;
		}
	}
	
	//Sign out a User
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
