package au.edu.unsw.cse.topfeeds.model;

import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;

public class Account {
	private int id;
	private int userId;
	private String username;
	private String accessToken;
	private String tokenSecret;
	private SocialNetwork type;
	private String name;

	public Account() {
	}

	public Account(int id, int userId, String username, String accessToken,
			String tokenSecret, SocialNetwork type, String name) {
		super();
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.accessToken = accessToken;
		this.tokenSecret = tokenSecret;
		this.type = type;
		this.name = name;
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

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public SocialNetwork getType() {
		return type;
	}

	public void setType(SocialNetwork type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
