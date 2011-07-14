package au.edu.unsw.cse.topfeeds.dao.external;

import java.util.List;

import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public interface SocialDataAccess {

	List<Post> getUserFeed(Account account);
	List<SocialDistance> getSocialDistance(Account account);

}
