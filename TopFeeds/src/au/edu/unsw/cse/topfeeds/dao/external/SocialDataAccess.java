package au.edu.unsw.cse.topfeeds.dao.external;

import java.util.List;

import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public interface SocialDataAccess {

	/* Returns list of Recent posts on account */
	List<Post> getUserFeed(Account account);
	
	/* Returns list of Social Relationships on account*/
	List<SocialDistance> getSocialDistance(Account account);
	
	/* Return Account of user giving accessToken and tokenSecret*/
	Account getUserAccount(Integer userId, String accessToken, String tokenSecret);
}
