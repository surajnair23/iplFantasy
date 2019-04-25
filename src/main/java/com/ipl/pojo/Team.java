package com.ipl.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Team")
public class Team {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name="teamId")
    private long teamId;
	
	@Column(name="teamName")
	private String teamName;
	
	@Column(name="teamAbv")
	private String teamAbv;
	
	@Column(name="homeCity")
	private String homeCity;

	@Column(nullable = false)
 	private Date createdDate;
 	
 	@Column(nullable = false)
 	private Date udpatedDate;
 	
	public Team() {
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamAbv() {
		return teamAbv;
	}

	public void setTeamAbv(String teamAbv) {
		this.teamAbv = teamAbv;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUdpatedDate() {
		return udpatedDate;
	}

	public void setUdpatedDate(Date udpatedDate) {
		this.udpatedDate = udpatedDate;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}
	
	
}
