package au.edu.unsw.cse.topfeeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import au.edu.unsw.cse.topfeeds.dao.DatabaseConnection;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class FeedDAOImpl implements FeedDAO{

	private Connection conn = DatabaseConnection.getConnection();

	@Override
	public SocialDistance getSocialDistance(int ownerId, int friendId)
			throws Exception {

		SocialDistance sd = null;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, accountId, friendId, mutualFriends, interactions, LastUpdated " +
					"FROM SOCIAL_DISTANCE " +
					"WHERE accountId=? AND friendId=? ");
			ps.setInt(1, ownerId);
			ps.setInt(2, friendId);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.first()){
				sd = new SocialDistance();
				sd.setId(rs.getInt(1));
				sd.setAccountId(rs.getInt(2));
				sd.setFriendId(rs.getInt(3));
				sd.setMutualFriends(rs.getInt(4));
				sd.setInteractions(rs.getInt(5));
				sd.setLastUpdated(rs.getDate(6));
			}
			
			
		} catch (SQLException e) {
			throw new Exception("Failed to get Social Distance between the users");
		}
		return sd;
	}

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

}
