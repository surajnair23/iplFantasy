package com.ipl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Playerpoints")
public class Playerpoints {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name="ppid")
    private long ppid;
	
	@OneToOne
	private Fixture fixture;
	
	@OneToOne
	private Player player;
	
	@Column(name="points")
	private long points;

	public long getPpid() {
		return ppid;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public Player getPlayer() {
		return player;
	}

	public long getPoints() {
		return points;
	}

	public void setPpid(long ppid) {
		this.ppid = ppid;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
	
}
