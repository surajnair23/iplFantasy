package com.ipl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;	

@Entity
@Table(name="Player")
public class Player {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name="playerId")
    private long playerId;
	
	@Column(name="pName")
	private String playerName;
	
	@Column(name="pType")
	private String playerType;
	
	@Column(name="pCap")
	private boolean playerCaptain;
	
	@Column(name="pWk")
	private boolean playerKeeper;
	
	//mention the third column name containing the one Id
	@OneToOne
    private Team team;

	public Player() {
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerType() {
		return playerType;
	}

	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}

	public boolean isPlayerCaptain() {
		return playerCaptain;
	}

	public void setPlayerCaptain(boolean playerCaptain) {
		this.playerCaptain = playerCaptain;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean isPlayerKeeper() {
		return playerKeeper;
	}

	public void setPlayerKeeper(boolean playerKeeper) {
		this.playerKeeper = playerKeeper;
	}
	
	
}
