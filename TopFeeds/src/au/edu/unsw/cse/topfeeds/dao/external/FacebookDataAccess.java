package au.edu.unsw.cse.topfeeds.dao.external;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.dao.impl.FeedDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class FacebookDataAccess implements SocialDataAccess{

	private FeedDAO feedDAO = new FeedDAOImpl();
	
	@Override
	public List<Post> getUserFeed(Account account) {
		// TODO Auto-generated method stub
		String accesstoken = account.getAccessToken();
		
		FacebookClient facebookClient = new DefaultFacebookClient(
				accesstoken);
		
		Connection<com.restfb.types.Post> filteredFeed = facebookClient.fetchConnection(
				"me/home", com.restfb.types.Post.class);
		ArrayList<Post> posts = new ArrayList<Post>();
		
		for (com.restfb.types.Post p : filteredFeed.getData()) {
			System.out.println(p.toString());
			Post newPost = new Post();
			
			if (p.getComments()!=null){
				newPost.setComments(p.getComments().getCount().intValue());
			}
			
			newPost.setLikes(p.getLikesCount().intValue());
			newPost.setMessage(p.getMessage());
			newPost.setUrl(p.getLink());
			
			//need to insert user as friend with username
			int senderId = feedDAO.getFriendId(p.getFrom().getId(), SocialNetwork.FACEBOOK);
			newPost.setSenderId(senderId);
			newPost.setOwnerId(account.getId());
			newPost.setType(p.getType());
			
			posts.add(newPost);
		}
		
		return posts;
	}

	@Override
	public List<SocialDistance> getSocialDistance(Account account) {
		// TODO Auto-generated method stub
		return null;
	}


}
