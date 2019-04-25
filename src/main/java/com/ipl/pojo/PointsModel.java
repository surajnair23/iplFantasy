package com.ipl.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//model for display
public class PointsModel {
	
	private User user;
	private Fixture fixture;
	private List<Playerpoints> playerpoints;
	private Set<Player> players = new HashSet<Player>();
	
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	public User getUser() {
		return user;
	}
	public Fixture getFixture() {
		return fixture;
	}
	public List<Playerpoints> getPlayerpoints() {
		return playerpoints;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
	public void setPlayerpoints(List<Playerpoints> playerpoints) {
		this.playerpoints = playerpoints;
	}
	
	
	
}
