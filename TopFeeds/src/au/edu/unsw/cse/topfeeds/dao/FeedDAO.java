package au.edu.unsw.cse.topfeeds.dao;

import java.util.List;

import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public interface FeedDAO {
	
	void updatePosts(List<Post> posts);
	List<Post> retrieveFeedRanked(int userId, int page);
	List<Post> retrieveFeedUnranked(int userId, int page);

	void updateSocialScore(List<SocialDistance> socialDistances);
	SocialDistance getSocialDistance(int ownerId, int friendId) throws Exception;
	
}
