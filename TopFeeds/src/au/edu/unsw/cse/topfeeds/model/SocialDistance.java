package au.edu.unsw.cse.topfeeds.model;

public class SocialDistance {
	private int id;
	private int accountId;
	private int friendId;
	private int mutualFriends;
	private int interactions;
	
	public SocialDistance(int id, int accountId, int friendId,
			int mutualFriends, int interactions) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.friendId = friendId;
		this.mutualFriends = mutualFriends;
		this.interactions = interactions;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

}
