package com.ipl.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ipl.dao.TeamDao;
import com.ipl.pojo.Fixture;
import com.ipl.pojo.Player;
import com.ipl.pojo.Playerpoints;
import com.ipl.validator.TeamValidator;


//*-----------------|-------------------|--------------*//
//matchCentre.htm	| matchCentre.jsp	| list of matches   
//matchupdate.htm	| void				| update the winner
//scoreboard.htm	| score.jsp			| player points update


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
		@RequestMapping(value="matchupdate.htm",method = RequestMethod.GET)
		public void updateMatch(HttpServletRequest request,Model model){
			
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("admin") != null) { 
					String matchId = request.getParameter("matid");
					String winner = request.getParameter("winteam");
					boolean updated = teamdao.updateTeam(matchId,winner);
					if(!updated)
						{System.out.println("Value did not update");}
				}
			}
		}
		
		//Scoreboard update
		@RequestMapping(value="scoreboard.htm",method =RequestMethod.GET)
		public String updatePlayerscore(Model model, HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("admin") != null) {
					String matchId = request.getParameter("matid");
					String matchINnew = matchId == null ? (String)session.getAttribute("matchid"):matchId;
					String val = matchINnew.replace("\'", "");
					//use the matchId to fetch everyone
					Fixture fixture= teamdao.getCurrentMatch(val);
					model.addAttribute("fixture",fixture);
					
					//check if points are already updated
					List<Playerpoints> pplist = teamdao.filledPoints(fixture);
					if(null != pplist) {
						model.addAttribute("points", pplist);
					}else {
						//else move on to create command class
						List<Player> team1Player = teamdao.getPlayers4Team(fixture.getTeam1());
						List<Player> team2Player = teamdao.getPlayers4Team(fixture.getTeam2());
						List<Playerpoints> playerpoints = teamdao.getPlayerpoints(fixture,team1Player,team2Player);
						model.addAttribute("fillout", playerpoints);
						model.addAttribute("playerpoints", new Playerpoints());
						session.setAttribute("matchid", val);
					}
					return "score";
				}
			}
			
			return "score";
		}
		
		@RequestMapping(value="scoreboard.htm",method =RequestMethod.POST)
		public String postPlayerscore(Model model, HttpServletRequest request) {
			System.out.println("data fetched");
			HttpSession session = request.getSession(false);
			Map<String,String[]> reqMap = request.getParameterMap();
			String val = (String)session.getAttribute("matchid");
			
			if(session != null) {
				if(session.getAttribute("admin") != null) {
					Fixture fixture= teamdao.getCurrentMatch(val);
					//parse to player points
					List<Playerpoints> playerpointList = teamdao.readMap(reqMap,fixture);
					boolean isSaved = teamdao.pointsSave(playerpointList);
					return "redirect:matchCentre.htm";
				}
			}
			return pgntfn;
		}
		
		
}
