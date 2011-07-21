package au.edu.unsw.cse.topfeeds.dao;

import java.util.List;

import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public interface FeedDAO {
	
	void addPost(Post post);
	List<Post> retrieveFeed(String accountId);
	void updateSocialScore(List<SocialDistance> socialDistances);
	SocialDistance getSocialDistance(int ownerId, int friendId) throws Exception;
	
}
