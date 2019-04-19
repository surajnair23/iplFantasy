package com.ipl.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany
	private Set<Player> playerSet = new HashSet<Player>();

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
	
	
}
