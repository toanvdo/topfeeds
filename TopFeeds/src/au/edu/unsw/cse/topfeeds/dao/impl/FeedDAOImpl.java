package au.edu.unsw.cse.topfeeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.cse.topfeeds.dao.DatabaseConnection;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class FeedDAOImpl implements FeedDAO {

	private Connection conn = DatabaseConnection.getConnection();

	public SocialDistance getSocialDistance(int ownerId, int friendId)
			throws Exception {

		SocialDistance sd = null;
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT accountId, friendId, mutualFriends, interactions, LastUpdated "
							+ "FROM SOCIAL_DISTANCE "
							+ "WHERE accountId=? AND friendId=? ");
			ps.setInt(1, ownerId);
			ps.setInt(2, friendId);

			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				sd = new SocialDistance();
				sd.setAccountId(rs.getInt(2));
				sd.setFriendId(rs.getInt(3));
				sd.setMutualFriends(rs.getInt(4));
				sd.setInteractions(rs.getInt(5));
				sd.setLastUpdated(rs.getDate(6));
			}

		} catch (SQLException e) {
			throw new Exception(
					"Failed to get Social Distance between the users");
		}
		return sd;
	}

	public List<Post> retrieveFeedRanked(int userId, int page) {
		PreparedStatement ps;
		List<Post> posts = new ArrayList<Post>();
		try {
			ps = conn
					.prepareStatement("call UpdatePostScores(?,?)");
			ps.setInt(1, userId);
			ps.setInt(2, page*25);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt(1));
				post.setPostId(rs.getString(2));
				post.setOwnerId(rs.getInt(3));
				post.setSenderId(rs.getInt(4));
				post.setMessage(rs.getString(5));
				post.setUrl(rs.getString(6));
				post.setComments(rs.getInt(7));
				post.setLikes(rs.getInt(8));
				post.setType(rs.getString(9));
				post.setCreatedTime(rs.getDate(10));
				post.setScore(rs.getDouble(11));
				post.setLastUpdated(rs.getDate(12));
				post.setSenderName(rs.getString(13));
				posts.add(post);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posts;
	}
	
	public List<Post> retrieveFeedUnranked(int userId, int page) {
		PreparedStatement ps;
		List<Post> posts = new ArrayList<Post>();
		try {
			ps = conn
					.prepareStatement("SELECT post.id, postId, ownerId, senderId, message, url, comments, likes, post.type, createdTime, score, lastUpdated, realName "
							+ "FROM post INNER JOIN account ON post.ownerId = account.id INNER JOIN SOCIAL_NETWORK_USER ON post.senderId = SOCIAL_NETWORK_USER.id "
							+ "WHERE account.userId =1 ORDER BY createdTime DESC LIMIT 0 ,25");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt(1));
				post.setPostId(rs.getString(2));
				post.setOwnerId(rs.getInt(3));
				post.setSenderId(rs.getInt(4));
				post.setMessage(rs.getString(5));
				post.setUrl(rs.getString(6));
				post.setComments(rs.getInt(7));
				post.setLikes(rs.getInt(8));
				post.setType(rs.getString(9));
				post.setCreatedTime(rs.getDate(10));
				post.setScore(rs.getDouble(11));
				post.setLastUpdated(rs.getDate(12));
				post.setSenderName(rs.getString(13));
				posts.add(post);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posts;
	}


	public void updateSocialScore(List<SocialDistance> socialDistances) {
		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT SOCIAL_DISTANCE (accountId, friendId, mutualFriends, interactions, lastUpdated)"
							+ " VALUES(?,?,?,?,NOW()) "
							+ "ON DUPLICATE KEY UPDATE mutualFriends=?, interactions=?, lastUpdated=NOW()");
			for (SocialDistance sd : socialDistances) {
				ps.setInt(1, sd.getAccountId());
				ps.setInt(2, sd.getFriendId());
				ps.setInt(3, sd.getMutualFriends());
				ps.setInt(4, sd.getInteractions());

				ps.setInt(5, sd.getMutualFriends());
				ps.setInt(6, sd.getInteractions());
				ps.addBatch();
			}
			int[] updateCount = ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePosts(List<Post> posts) {
		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT POST (postId,ownerId,senderId,message,url,comments,likes,type,createdTime,score,lastUpdated)"
							+ " VALUES(?,?,?,?,?,?,?,?,?,?,NOW()) "
							+ "ON DUPLICATE KEY UPDATE postId=?");
			for (Post p : posts) {
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
