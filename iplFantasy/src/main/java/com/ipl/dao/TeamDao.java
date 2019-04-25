package com.ipl.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

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
	
	public Fixture getCurrentMatch(String matchId){
		Long mid = Long.parseLong(matchId);
		@SuppressWarnings("deprecation")
		Criteria cfix = getSession().createCriteria(Fixture.class);
		cfix.add(Restrictions.eq("matchId", mid));
		Fixture f = (Fixture) cfix.uniqueResult();
		return f;
	}
	
	public List<Player> getPlayers4Team(Team teamObj){
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
		Fixture fixture = getCurrentMatch(matchid);
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

	public List<Userselection> createdTeamPerUser(User user){
		Criteria c = getSession().createCriteria(Userselection.class);
		c.add(Restrictions.eq("user",user));
		
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
	
	public List<Playerpoints> getPlayerpoints(Fixture fixture,List<Player> team1players, List<Player> team2players) {
		
		List<Playerpoints> plist = new ArrayList<Playerpoints>();
		
		for(Player p:team1players) {
			Playerpoints plyrpts = new Playerpoints();
			plyrpts.setFixture(fixture);
			plyrpts.setPlayer(p);
			plyrpts.setPoints(0);
			plyrpts.setTeam(fixture.getTeam1());
			plist.add(plyrpts);
		}
		for(Player p: team2players) {
			Playerpoints plyrpts = new Playerpoints();
			plyrpts.setFixture(fixture);
			plyrpts.setPlayer(p);
			plyrpts.setPoints(0);
			plyrpts.setTeam(fixture.getTeam2());
			plist.add(plyrpts);
		}
		return plist;
	}
	
	public List<Playerpoints> filledPoints(Fixture fixture) {
		Criteria cfix = getSession().createCriteria(Playerpoints.class);
		cfix.add(Restrictions.eq("fixture",fixture));
		List pp = cfix.list();
			if(null != pp && pp.size()>0) {
				return pp;
			}else {
				return null;
			}
	}
	
	public static Player fetchPlayer(Long plyrid) {
		Criteria cplay = getSession().createCriteria(Player.class);
		cplay.add(Restrictions.idEq(plyrid));
		
		return (Player)cplay.uniqueResult();
	}
	
	public List<Playerpoints> readMap(Map<String, String[]> reqMap,Fixture fixture) {
		List<Playerpoints> playerpoints = new ArrayList<Playerpoints>();
//		http://theopentutorials.com/examples/java-ee/servlet/get-all-parameters-in-html-form-using-getparametermap/
		Set set = reqMap.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, String[]> entry = 
	                (Entry<String, String[]>) it.next();
			Playerpoints plyrpts = new Playerpoints();
			long playerId = Long.parseLong(entry.getKey());
			String[] paramValues = entry.getValue();
			String paramValue = ""; long pts;
			if (paramValues.length == 1) {
                paramValue = paramValues[0];
            }
			if(paramValue.equals("")) {
				pts = 0;
			}else {
				pts = Long.parseLong(paramValue);
			}
			
			plyrpts.setPlayer(fetchPlayer(playerId));
			plyrpts.setFixture(fixture);
			plyrpts.setTeam(fetchPlayer(playerId).getTeam());
			plyrpts.setPoints(pts);
			playerpoints.add(plyrpts);
		}

//		for (Entry<String, String[]> entry : reqMap.entrySet()) {
//			Playerpoints plyrpts = new Playerpoints();
//			long playerId = Long.parseLong(entry.getKey());
//			long pts;
//			
//			if(entry.getValue() != null) { 
//				pts = 0;
//			}else {
//				pts = Long.parseLong(entry.getValue());
//			}
//			plyrpts.setPlayer(fetchPlayer(playerId));
//			plyrpts.setFixture(fixture);
//			plyrpts.setTeam(fetchPlayer(playerId).getTeam());
//			plyrpts.setPoints(pts);
//			playerpoints.add(plyrpts);
//		}
		return playerpoints;
	}
	
	public boolean pointsSave(List<Playerpoints> playerpointslist) {
		try {
			for(Playerpoints p:playerpointslist) {
				begin();
				getSession().save(p);
				commit();
			}
			return true;
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	public List<Object[]> getAllUsers(){
		String hql = "SELECT sum(p.points),u.user_userId from playerpoints p, userselection u  where  p.fixture_matchId = u.fixture_matchId AND u.user_userId in (select userId from User)";
		Query q = getSession().createNativeQuery(hql);
		List<Object[]> mapOfUsrPoints = q.list();
//		for(Object o :mapOfUsrPoints) {
//			Map<Long, long>  
//		}
		return null;
	}
}
