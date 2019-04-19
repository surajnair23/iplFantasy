package com.ipl.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ipl.dao.*;
import com.ipl.pojo.*;
import com.ipl.validator.*;
import com.sun.net.httpserver.Authenticator.Success;

@Controller
public class MatchController {
	
	@Autowired
	TeamDao teamdao;

	@Autowired
	TeamValidator teamValidator;
	
	private static final String pgntfn = "redirect:pageNtFound.htm";
	
	//add matches, send email to all involved parties
		@RequestMapping(value="matchCentre.htm",method = RequestMethod.GET)
		public String matchCentre(HttpServletRequest request, Model model) {
			String route = "matchCentre";
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("admin") != null) { 
					List<Fixture> fixtures = teamdao.getFixture();
					model.addAttribute("fixtures",fixtures);
					return route; }
				else { return pgntfn; }
			}
			return pgntfn;
		}
		
		//save matches
		@RequestMapping(value="matchCentre.htm",method = RequestMethod.POST)
		public String saveMatch(HttpServletRequest request,Model model) {
			String route = "matchCentre";
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("admin") != null) { 
					String team1 = request.getParameter("team1");
					String team2 = request.getParameter("team2");
					String city = request.getParameter("city");	
					String dat = request.getParameter("currdate")+" 08:00:00";
					String validData = teamValidator.formValidate(team1,team2,city);
					if(validData != null) {
						request.setAttribute("success", validData);
						return route;
					}else {
						boolean saved = false;
						//formatting date for sql
						try {
							Date sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dat);
							saved = teamdao.matchSave(team1,team2,city,sdf);
						} catch (ParseException e) {e.printStackTrace();}
						
						if(saved) {
							request.setAttribute("success", "Match info saved");
							List<Fixture> fixtures = teamdao.getFixture();
							model.addAttribute("fixtures",fixtures);
							return route;
						}else {
							request.setAttribute("success", "Error saving info");
							return route;
						}
					}
				}
				else { return pgntfn; }
			}
			return pgntfn;
		}
		
		//update match winner /ipl/user/updatematch.htm?matid=6&winteam=2
		
		@RequestMapping(value="matchupdate.htm",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value=HttpStatus.OK)
		public @ResponseBody ResponseEntity<?> updateMatch(HttpServletRequest request,Model model,@RequestBody String matid,@RequestBody String winteam){
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("admin") != null) { 
//					String matchId = request.getParameter("matid");
//					String winner = request.getParameter("winteam");
					boolean updated = teamdao.updateTeam(matid,winteam);
					if(updated) {
						List<Fixture> fixtures = teamdao.getFixture();
						model.addAttribute("fixtures",fixtures);
						return new ResponseEntity<Success>(HttpStatus.OK);
					}
				}
			}
			return new ResponseEntity<Error>(HttpStatus.OK);
		}
		
		
}
