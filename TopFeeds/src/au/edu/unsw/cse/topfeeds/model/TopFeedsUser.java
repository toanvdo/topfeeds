package au.edu.unsw.cse.topfeeds.model;

import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;

public class TopFeedsUser {

	private int id;
	private String username;
	private String password;
	private String email;
	private String mac;
	private String status;
	private String name;

	public TopFeedsUser() {
	}

	public TopFeedsUser(int id, String username, String password, String email,
			String mac, String status, String name) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.mac = mac;
		this.status = status;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
