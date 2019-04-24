package com.ipl.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.ipl.pojo.Fixture;
import com.ipl.pojo.Player;
import com.ipl.pojo.Playerpoints;
import com.ipl.pojo.PointsModel;
import com.ipl.pojo.Team;
import com.ipl.pojo.User;
import com.ipl.pojo.Userselection;

public class TeamDao extends Dao{

	public TeamDao(){
	}
	
	public boolean saveTeam(String abv,String name,String pName[],String tPlayer[],String captain,String wk,String homecity) {
		boolean result = false;
		
		//parsing information
		Team team = new Team();
		team.setTeamAbv(abv);
		team.setTeamName(name);
		team.setHomeCity(homecity);
		//formatting date for sql
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format(dt);
		team.setCreatedDate(dt);
		team.setUdpatedDate(dt);
		
		int capNum = Integer.valueOf(captain) - 1;
		int wkNum = Integer.valueOf(wk) - 1;
		try {
			
			begin();
			getSession().save(team);
			
		for(int i=0;i<11;i++) {
			Player player = new Player();
			player.setPlayerName(pName[i]);
			player.setPlayerType(tPlayer[i]);
			if(i==capNum) {
				player.setPlayerCaptain(true);
			}
			if(i==wkNum) {
				player.setPlayerKeeper(true);
			}
			player.setTeam(team);
			getSession().save(player);
		}
			
			commit();
			result = true;
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
		}finally {
			close();
		}
		//trying to save object
		
		return result;
	}
	
	public boolean matchSave(String team1,String team2,String city,Date dat) {
		boolean result = false;
			@SuppressWarnings("deprecation")
			Criteria c1 = getSession().createCriteria(Team.class);
			c1.add(Restrictions.ilike("teamName", team1));
			c1.setMaxResults(1);
			Team teamOne = (Team) c1.uniqueResult();
			
			@SuppressWarnings("deprecation")
			Criteria c2 = getSession().createCriteria(Team.class);
			c2.add(Restrictions.ilike("teamName", team2));
			c2.setMaxResults(1);
			Team teamTwo = (Team) c2.uniqueResult();
			
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.format(dt);
			Fixture fixture = new Fixture();
			fixture.setCreatedDate(dt);
			fixture.setUpdatedDate(dt);
			fixture.setTeam1(teamOne);
			fixture.setTeam2(teamTwo);
			fixture.setMatchDate(dat);
			fixture.setVenue(city);
			fixture.setWinTeam(null);
			try {
				begin();
				getSession().save(fixture);
				commit();
				result = true;
			}catch(Exception e) {
				rollback();
				e.printStackTrace();
			}finally {
				close();
			}
		return result;
	}
	
	public boolean updateTeam(String matchId, String winner) {
		boolean result = false;
		long matchidty = Long.parseLong(matchId);
		long teamidty = Long.parseLong(winner);

		@SuppressWarnings("deprecation")
		Criteria cMatch = getSession().createCriteria(Fixture.class);
		cMatch.add(Restrictions.eq("matchId", matchidty));
		cMatch.setMaxResults(1);
		Fixture fixture = (Fixture) cMatch.uniqueResult();
		
		@SuppressWarnings("deprecation")
		Criteria cteam = getSession().createCriteria(Team.class);
		cteam.add(Restrictions.eq("teamId", teamidty));
		cteam.setMaxResults(1);
		Team team = (Team) cteam.uniqueResult();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format(date);
		fixture.setWinTeam(team);
		fixture.setUpdatedDate(date);
		try {
			begin();
			getSession().update(fixture);
			commit();
			result = true;
		}catch(Exception e){
			rollback();
			e.printStackTrace();
			return result;
		}finally {
			close();
		}
		return result;
	}
	
	public List<Fixture> getFixture(){
		@SuppressWarnings("deprecation")
		Criteria cMatch = getSession().createCriteria(Fixture.class);
		@SuppressWarnings("unchecked")
		List<Fixture> fixtures = cMatch.list();
		return fixtures;
	}
	
	public Fixture registerMatch(String matchId){
		Long mid = Long.parseLong(matchId);
		@SuppressWarnings("deprecation")
		Criteria cfix = getSession().createCriteria(Fixture.class);
		cfix.add(Restrictions.eq("matchId", mid));
		Fixture f = (Fixture) cfix.uniqueResult();
		return f;
	}
	
	public List<Player> getPlayers(Team teamObj){
		@SuppressWarnings("deprecation")
		Criteria play = getSession().createCriteria(Player.class);
		play.add(Restrictions.eq("team", teamObj));
		@SuppressWarnings("unchecked")
		List<Player> players = play.list();
		return players;
	}
	
	@SuppressWarnings("unused")
	public boolean saveSelection(String Playerid[],String winner, String matchid, User user) {
		long matchId = Long.parseLong(matchid);
		long winnerId = Long.parseLong(winner);
		Userselection userselection = new Userselection();
		
		//USESRSELECTION FILLING OUT PLAYER SET***********
		Player player;
		Set<Long> pids = new HashSet<Long>();
		//create an array of long ids
		for(int i=0;i<Playerid.length;i++) {
			pids.add(Long.parseLong(Playerid[i]));
			System.out.println(pids.toString());
		}
		Criteria cp = getSession().createCriteria(Player.class);
		cp.add(Restrictions.in("playerId", pids));
		List players = cp.list();
		Set<Player> pset = new HashSet<Player>(players);
		//USESRSELECTION FILLING OUT PLAYER SET************
		
		//USERSELECTION SETTING UP MATCH
		Fixture fixture = registerMatch(matchid);
		//USERSELECTION SETTING UP MATCH
		
		//USERSELECTION SETTING UP WINNER
		Criteria ct = getSession().createCriteria(Team.class);
		ct.add(Restrictions.idEq(winnerId));
		Team t = (Team) ct.uniqueResult();
		
		//Verify if a team is already selected with this id
		boolean selexixts = selectionExists(user,fixture);
		
		if(selexixts) {
			return false;
		}else {
			userselection.setPlayerSet(pset);	
			userselection.setFixture(fixture);
			userselection.setWinner(t);
			userselection.setUser(user);
			try {
				begin();
				getSession().save(userselection);
				commit();
				return true;
			}catch(HibernateException e) {
				rollback();
				e.printStackTrace();
			}finally {
				close();
			}
			return false;
		}
	}

	public List<Userselection> registred(User user){
		Criteria c = getSession().createCriteria(Userselection.class);
		List<Userselection> usersel = c.list();
		return usersel;
	}
	
	public boolean selectionExists(User user,Fixture fixture) {
		Criteria cuser = getSession().createCriteria(Userselection.class);
		cuser.add(Restrictions.eq("user",user));
		cuser.add(Restrictions.eq("fixture",fixture));
		List result = cuser.list();
		if(result.size() > 0) {
			//user has already playd for this match
			return true;
		}else {
			return false;
		}
		
	}
	
	public List<PointsModel> getPoints(List<Userselection> userselection, User user){
		//you should fetch the fixtures and players and get only their points from the table
		List<PointsModel> pointsModel = new ArrayList<PointsModel>();
		List<Playerpoints> plyerp = new ArrayList<Playerpoints>();
		
		for(Userselection usersel:userselection) {
			//loop through the user selection
			PointsModel pmo = new PointsModel();
			pmo.setUser(user);
			Fixture fixture = usersel.getFixture();
			pmo.setFixture(fixture);
			pmo.getPlayers().addAll(usersel.getPlayerSet());
			for(Player p:usersel.getPlayerSet()) {
				Criteria cp = getSession().createCriteria(Playerpoints.class);
				cp.add(Restrictions.eq("player", p));
				cp.add(Restrictions.eq("fixture", fixture));
				cp.setMaxResults(1);
				Playerpoints playerpoints = (Playerpoints) cp.uniqueResult();
				plyerp.add(playerpoints);
			}
			System.out.println("Player points collected plyerp");
			System.out.println("we will ne checking if this set contains any null value, if it does not we will set it to pmo");
			if(!plyerp.contains(null)) {
				pmo.setPlayerpoints(plyerp);
			}
			System.out.println("Number of players:"+pmo.getPlayers().size());
			pointsModel.add(pmo);
		}
		
		if(pointsModel.size() <= 0) {
			return null;
		}else {
			return pointsModel;
		}
	} 
}
