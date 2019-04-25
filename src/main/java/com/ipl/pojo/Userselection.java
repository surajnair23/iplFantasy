package com.ipl.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Userselection")
public class Userselection {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name="selectionId")
    private long selectionId;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Fixture fixture;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Usersel_Players", 
             joinColumns = { @JoinColumn(name = "selectionId") }, 
             inverseJoinColumns = { @JoinColumn(name = "playerId") })
	private Set<Player> playerSet;
	
	@OneToOne
	private Team winner;

	/**
	 * @return the winner
	 */
	public Team getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(Team winner) {
		this.winner = winner;
	}

	/**
	 * @return the selectionId
	 */
	public long getSelectionId() {
		return selectionId;
	}

	/**
	 * @param selectionId the selectionId to set
	 */
	public void setSelectionId(long selectionId) {
		this.selectionId = selectionId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the fixture
	 */
	public Fixture getFixture() {
		return fixture;
	}

	/**
	 * @param fixture the fixture to set
	 */
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	/**
	 * @return the playerSet
	 */
	public Set<Player> getPlayerSet() {
		return playerSet;
	}

	/**
	 * @param playerSet the playerSet to set
	 */
	public void setPlayerSet(Set<Player> playerSet) {
		this.playerSet = playerSet;
	}
	
	// you will also have a player points table,  playrid, matchid, points
}
