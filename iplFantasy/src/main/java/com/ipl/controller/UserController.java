package com.ipl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ipl.dao.TeamDao;
import com.ipl.pojo.Fixture;

@Controller
@RequestMapping(value="user")
public class UserController {
	
	private static final String pgntfn = "404PageNotFound";
	
	@Autowired
	TeamDao teamdao;
	
	//user home
	@RequestMapping(value="/match.htm",method = RequestMethod.GET)
	public String matchCentre(Model model,HttpServletRequest request) {
		String route = "match";
		HttpSession session = request.getSession(false);
		if(session != null) {
			if(session.getAttribute("user") != null) { 
				List<Fixture> fixtures = teamdao.getFixture();
				model.addAttribute("fixtures",fixtures);
				return route; 
			}
			else { return pgntfn; }
		}
		return pgntfn;
	}
	
	@RequestMapping(value="/teamselection.htm?id={id}",method=RequestMethod.GET)
	public String teamSel(Model model,HttpServletRequest request) {
		String route = "sel";
		String matchId = request.getParameter("id");
		HttpSession session = request.getSession(false);
		if(session != null) {
			if(session.getAttribute("user") != null) { 
//				List<Fixture> fixtures = teamdao.registerMatch(matchId);
//				model.addAttribute("fixtures",fixtures);
				
				return route;
			}
		}
		return route;
	}
	
	//update user details -------------> INCOMPLETE
		@RequestMapping(value="update.htm",method = RequestMethod.GET)
		public String updateUser(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("user") != null) {
					return "update";
				}else {
					return pgntfn;
				}
			}
			return pgntfn;
		}
		
}
