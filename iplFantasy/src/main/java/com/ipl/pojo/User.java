package com.ipl.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class User {
	
	//Generation type identity makes this the primary key
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	@Column(name="userId")
	    private long userId;

	 	@Column(nullable = false)
	 	private String fName;
	 	
	 	@Column(nullable = false)
	 	private String lName;

	 	@Column(nullable = false)
	 	private String phone;
	 	
	 	@Column(nullable = false)
	 	private String email;
	 	
	 	@Column(nullable = false)
	 	private String password;
	 	
	 	//,columnDefinition = "boolean default false"
	 	@Column(nullable = false)
	 	private Boolean isAdmin;

	 	@Column(nullable = false)
	 	private Boolean isApproved;
	 	
	 	@Column(nullable = false)
	 	private Date createdDate;
	 	
	 	@Column(nullable = false)
	 	private Date udpatedDate;
	 	
	 	@Column(nullable = false)
	 	private String username;
	 	
	 	public User() {
		}

		public Boolean getIsAdmin() {
			return isAdmin;
		}

		public void setIsAdmin(Boolean isAdmin) {
			this.isAdmin = isAdmin;
		}

		public Boolean getIsApproved() {
			return isApproved;
		}

		public void setIsApproved(Boolean isApproved) {
			this.isApproved = isApproved;
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

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getfName() {
			return fName;
		}

		public void setfName(String fName) {
			this.fName = fName;
		}

		public String getlName() {
			return lName;
		}

		public void setlName(String lName) {
			this.lName = lName;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	 	
}
