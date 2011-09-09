package au.edu.unsw.cse.topfeeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import au.edu.unsw.cse.topfeeds.dao.DatabaseConnection;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class FeedDAOImpl implements FeedDAO{

	private Connection conn = DatabaseConnection.getConnection();

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

	public List<Post> retrieveFeed(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateSocialScore(List<SocialDistance> socialDistances) {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT SOCIAL_DISTANCE (accountId, friendId, mutualFriends, interactions, lastUpdated)" +
					" VALUES(?,?,?,?,NOW()) " +
					"ON DUPLICATE KEY UPDATE accountId=?, friendId=?");
			for(SocialDistance sd : socialDistances){
				ps.setInt(1, sd.getAccountId());
				ps.setInt(2, sd.getFriendId());
				ps.setInt(3, sd.getMutualFriends());
				ps.setInt(4, sd.getInteractions());
				
				ps.setInt(5, sd.getAccountId());
				ps.setInt(6, sd.getFriendId());
				ps.addBatch();
			}
			int[] updateCount = ps.executeBatch();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void updatePosts(List<Post> posts) {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT POST (postId,ownerId,senderId,message,url,comments,likes,type,createdTime,score,lastUpdated)" +
					" VALUES(?,?,?,?,?,?,?,?,?,?,NOW()) " +
					"ON DUPLICATE KEY UPDATE postId=?");
			for(Post p : posts){
				ps.setString(1, p.getPostId());
				ps.setInt(2, p.getOwnerId());
				ps.setInt(3, p.getSenderId());
				ps.setString(4, p.getMessage());
				ps.setString(5, p.getUrl());
				ps.setInt(6, p.getComments());
				ps.setInt(7, p.getLikes());
				ps.setString(8, p.getType());
				ps.setTimestamp(9, new Timestamp(p.getCreatedTime().getTime()));
				ps.setDouble(10, p.getScore());
				ps.setString(11, p.getPostId());
				ps.addBatch();
			}
			int[] updateCount = ps.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
