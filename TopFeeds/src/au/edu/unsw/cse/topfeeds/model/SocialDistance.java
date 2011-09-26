package au.edu.unsw.cse.topfeeds.model;

import java.util.Date;

public class SocialDistance {
	private int accountId;
	private int friendId;
	private int mutualFriends;
	private int interactions;
	private Date lastUpdated;
	
	public SocialDistance( int accountId, int friendId,
			int mutualFriends, int interactions,Date lastUpdated) {
		super();
		this.accountId = accountId;
		this.friendId = friendId;
		this.mutualFriends = mutualFriends;
		this.interactions = interactions;
		this.setLastUpdated(lastUpdated);
	}
	
	public SocialDistance() {
		// TODO Auto-generated constructor stub
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public int getMutualFriends() {
		return mutualFriends;
	}
	public void setMutualFriends(int mutualFriends) {
		this.mutualFriends = mutualFriends;
	}
	public int getInteractions() {
		return interactions;
	}
	public void setInteractions(int interactions) {
		this.interactions = interactions;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
