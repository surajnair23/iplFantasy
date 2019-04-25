package com.ipl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ipl.dao.TeamDao;
import com.ipl.validator.TeamValidator;

@Controller
public class TeamController {

	private static final String pgntfn = "redirect:pageNtFound.htm";
	@Autowired
	TeamDao teamdao;
	

	@Autowired
	TeamValidator teamValidator;
	//Add teams and players
		@RequestMapping(value="addTeam.htm",method=RequestMethod.GET)
		public String addTeam(ModelMap model,HttpServletRequest request){
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("admin") != null) {
					//routing to team page
					return "addTeam";
				}
				return pgntfn;
			}
			return pgntfn;
		}
		
		@RequestMapping(value="addTeam.htm",method=RequestMethod.POST)
		public String addTeamPost(ModelMap model,HttpServletRequest request){
			HttpSession session = request.getSession(false);
			boolean teamSave=false;
			if(session != null) {
				if(session.getAttribute("admin") != null) {
					//routing to team page
					//logic to process our request
					String tAbv = request.getParameter("tAbv");
					String tName = request.getParameter("tName");
					String pName[]= request.getParameterValues("pName");
					String tPlayer[] = request.getParameterValues("tPlayer");
					String homeCity = request.getParameter("homeGround");
					String captain = request.getParameter("captain");
					String wk = request.getParameter("wk");
					String validmessage = teamValidator.validateForm(tAbv,tName,pName,tPlayer,captain,wk,homeCity);
					if(validmessage != null) {
						//some validation error
						request.setAttribute("error", validmessage);
						return "addTeam";
					}else {
						teamSave = teamdao.saveTeam(tAbv,tName,pName,tPlayer,captain,wk,homeCity);
						//retracing back to add team page
						if(teamSave) {
							request.setAttribute("success", "Team Saved");
							return "addTeam";
						}else {
							request.setAttribute("error", "Failure to save, try again");
							return "addTeam";
						}
					}
				}
				return pgntfn;
			}
			return pgntfn;
		}
}
