package au.edu.unsw.cse.topfeeds.dao.impl;

import java.sql.Connection;
import java.util.List;

import au.edu.unsw.cse.topfeeds.dao.DatabaseConnection;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class FeedDAOImpl implements FeedDAO{

	private Connection conn = DatabaseConnection.getConnection();
	
	@Override
	public void addPost(Post post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> retrieveFeed(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSocialScore(List<SocialDistance> socialDistances) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFriendId(String identifier, SocialNetwork socialNetwork) {
		// TODO Auto-generated method stub
		return 0;
	}

}
