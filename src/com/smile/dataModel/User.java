package com.smile.dataModel;

// default package
// Generated Feb 9, 2018 2:57:47 AM by Hibernate Tools 5.2.8.Final

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private int id;
	private String userId;
	private String userPassword;
	private String userName;
	private String userEmail;
	private String userState;

	public User() {
	}

	public User(int id, String userId, String userPassword) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
	}

	public User(int id, String userId, String userPassword, String userName, String userEmail, String userState) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userState = userState;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

}