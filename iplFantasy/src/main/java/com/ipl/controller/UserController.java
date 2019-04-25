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
import com.ipl.pojo.Player;
import com.ipl.pojo.Playerpoints;
import com.ipl.pojo.PointsModel;
import com.ipl.pojo.Team;
import com.ipl.pojo.User;
import com.ipl.pojo.Userselection;
import com.ipl.validator.TeamValidator;


@Controller
@RequestMapping(value="user")
public class UserController {
	
	private static final String pgntfn = "404PageNotFound";
	
	@Autowired
	TeamDao teamdao;
	
	@Autowired
	TeamValidator teamvalidator;
	
	//match lists
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
	
	//make a team from a match
	@RequestMapping(value="/teamselection.htm",method=RequestMethod.GET)
	public String teamSel(Model model,HttpServletRequest request) {
		String route = "sel";
		HttpSession session = request.getSession(false);
		String matid = request.getParameter("matid");
		String matchId = matid == null ? (String)session.getAttribute("matchid"):matid;
		String val = matchId.replace("\'", "");
		if(session != null) {
			if(session.getAttribute("user") != null) { 
				if(matchId != null) {
					Fixture fixtures = teamdao.getCurrentMatch(val);
					List<Player> playerTeam1 = teamdao.getPlayers4Team((Team)fixtures.getTeam1());
					List<Player> playerTeam2 = teamdao.getPlayers4Team((Team)fixtures.getTeam2());
					model.addAttribute("teamsel",fixtures);
					model.addAttribute("pTeam1",playerTeam1);
					model.addAttribute("pTeam2",playerTeam2);
					return route;
				}
			}
		}
		return route;
	}
	
	@RequestMapping(value="/teamselection.htm",method=RequestMethod.POST)
	public String teamSelection(Model model,HttpServletRequest request) {
//		String route = "sel";
		HttpSession session = request.getSession(false);
		if(session != null) {
			if(session.getAttribute("user") != null) {
				String matchId = request.getParameter("matchId");
				String playerid[] = request.getParameterValues("playerid");
				String winner = request.getParameter("winner");
				User user = (User) session.getAttribute("user");
				String validate = teamvalidator.selectionValidate(playerid,winner);
				if(validate==null) {
					//no error hence save
					boolean saved = teamdao.saveSelection(playerid, winner, matchId,user);
					if(saved)
						{return "redirect:myselection.htm";}
					else {
						session.setAttribute("error", "Data already sent");
						session.setAttribute("matchid", matchId);
						return "redirect:teamselection.htm";
					}
				}else {
					//validation errors
					System.out.println(validate);
					session.setAttribute("error", validate);
					session.setAttribute("matchid", matchId);
					return "redirect:teamselection.htm";
				}
			}
		}
		
		return pgntfn;
	}
	//see the teams you've created
	@RequestMapping(value="/myselection.htm",method=RequestMethod.GET)
	public String getteamDashboard(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			if(session.getAttribute("user") != null) {
//				logic to display all teams
				User user = (User) session.getAttribute("user");
				List<Userselection> usersel = teamdao.createdTeamPerUser(user);
				if(usersel != null) {
					List<PointsModel> pointslist = teamdao.getPoints(usersel,user);
					model.addAttribute("points",pointslist);
				}else {
					List<PointsModel> pointslist = null;
					model.addAttribute("points",pointslist);
				}
				return "teamsel";
			}
		}
		return pgntfn;
	}
	
//	//actions on teams you've created
	@RequestMapping(value="/myselection.htm",method=RequestMethod.POST)
	public String postteamDashboard(Model model,HttpServletRequest request) 
	{
		HttpSession session = request.getSession(false);
		if(session != null) {
			if(session.getAttribute("user") != null) {
				//logic to save team
				
			}
		}
		return pgntfn;
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
		
		//leaderboard
		@RequestMapping(value="leaderboard.htm",method= RequestMethod.GET)
		public String getLeaderBoard(HttpServletRequest request, Model model) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				if(session.getAttribute("user") != null) {
					List<Object[]> listUsers = teamdao.getAllUsers();
					return "board";
				}
			}
			return pgntfn;
		}
		
}
