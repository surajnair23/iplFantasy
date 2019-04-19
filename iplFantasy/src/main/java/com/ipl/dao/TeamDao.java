package com.ipl.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.ipl.pojo.Fixture;
import com.ipl.pojo.Player;
import com.ipl.pojo.Team;

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
			Criteria c1 = getSession().createCriteria(Team.class);
			c1.add(Restrictions.ilike("teamName", team1));
			c1.setMaxResults(1);
			Team teamOne = (Team) c1.uniqueResult();
			
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
		Criteria cMatch = getSession().createCriteria(Fixture.class);
		cMatch.add(Restrictions.eq("matchId", matchidty));
		cMatch.setMaxResults(1);
		Fixture fixture = (Fixture) cMatch.uniqueResult();
		
		Criteria cteam = getSession().createCriteria(Team.class);
		cteam.add(Restrictions.eq("teamId", teamidty));
		cteam.setMaxResults(1);
		Team team = (Team) cteam.uniqueResult();
		
		fixture.setWinTeam(team);
		
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
		List<Fixture> fixtures = null;
		Criteria cMatch = getSession().createCriteria(Fixture.class);
		fixtures = cMatch.list();
		return fixtures;
	}
	
	public Fixture registerMatch(String matchId){
		Long mid = Long.parseLong(matchId);
		Criteria cfix = getSession().createCriteria(Fixture.class);
		cfix.add(Restrictions.eq("matchId", mid));
		Fixture f = (Fixture) cfix.uniqueResult();
		return f;
	}
}
