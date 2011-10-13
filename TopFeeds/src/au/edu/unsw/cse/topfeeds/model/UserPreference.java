package au.edu.unsw.cse.topfeeds.model;

import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;

public class UserPreference {

	private int userId;
	private float socialDistancePref;
	private float affinityPref;
	private float popularityPref;
	private SocialNetwork networkPref;
	private float recencyPref;
	private float mutualFriendsPref;

	public UserPreference(){}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getSocialDistancePref() {
		return socialDistancePref;
	}

	public void setSocialDistancePref(float socialDistancePref) {
		this.socialDistancePref = socialDistancePref;
	}

	public float getPopularityPref() {
		return popularityPref;
	}

	public void setPopularityPref(float popularityPref) {
		this.popularityPref = popularityPref;
	}

	public SocialNetwork getNetworkPref() {
		return networkPref;
	}

	public void setNetworkPref(SocialNetwork networkPref) {
		this.networkPref = networkPref;
	}

	public float getRecencyPref() {
		return recencyPref;
	}

	public void setRecencyPref(float recencyPref) {
		this.recencyPref = recencyPref;
	}

	public float getAffinityPref() {
		return affinityPref;
	}

	public void setAffinityPref(float affinityPref) {
		this.affinityPref = affinityPref;
	}

	public float getMutualFriendsPref() {
		return mutualFriendsPref;
	}

	public void setMutualFriendsPref(float mutualFriendsPref) {
		this.mutualFriendsPref = mutualFriendsPref;
	}

}
