package au.edu.unsw.cse.topfeeds.dao;

public enum SocialNetwork {
	FACEBOOK("facebook"), TWITTER("twitter");
	
	private String value;
	
	SocialNetwork(String value){
		this.value = value;
	}
	
	public String getValue(SocialNetwork network){
		return network.value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
