package au.edu.unsw.cse.topfeeds.model;

import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;

public class Account {
	private int id;
	private int userId;
	private String username;
	private String accessToken;
	private SocialNetwork type;
	
	public Account(){}
	
	public Account(int id, int userId, String username, String accessToken,
			SocialNetwork type) {
		super();
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.accessToken = accessToken;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public SocialNetwork getType() {
		return type;
	}
	public void setType(SocialNetwork type) {
		this.type = type;
	}
	
}
