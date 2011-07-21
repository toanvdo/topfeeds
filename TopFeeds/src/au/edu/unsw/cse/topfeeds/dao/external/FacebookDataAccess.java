package au.edu.unsw.cse.topfeeds.dao.external;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.dao.impl.FeedDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class FacebookDataAccess implements SocialDataAccess {

	private FeedDAO feedDAO = new FeedDAOImpl();
	private AccountDAO accountDAO = new AccountDAOImpl();

	@Override
	public List<Post> getUserFeed(Account account) {
		// TODO Auto-generated method stub
		String accesstoken = account.getAccessToken();

		FacebookClient facebookClient = new DefaultFacebookClient(accesstoken);

		Connection<com.restfb.types.Post> filteredFeed = facebookClient
				.fetchConnection("me/home", com.restfb.types.Post.class);
		ArrayList<Post> posts = new ArrayList<Post>();

		for (com.restfb.types.Post p : filteredFeed.getData()) {

			Post post = new Post();
			post.setPostId(p.getId());
			post.setOwnerId(account.getUserId());
			post.setMessage(p.getMessage());
			if (p.getLikesCount()!=null){
				post.setLikes(p.getLikesCount().intValue());
			}else{
				post.setLikes(0);
			}
			
			post.setUrl(p.getLink());
			if (p.getComments() != null) {
				post.setComments(p.getComments().getCount().intValue());
			} else {
				post.setComments(0);
			}
			
			// Check for sender id and then if there isnt any create one...
			int senderId =-1;
			try {
				senderId = accountDAO.getSocialNetworkUser(p.getFrom().getId(),
						SocialNetwork.FACEBOOK);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (senderId == -1) {
				try {
					senderId = accountDAO
							.registerFriend(p.getFrom().getId(),
									SocialNetwork.FACEBOOK, p.getFrom().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			post.setSenderId(senderId);

			// get social score, combine social distance and time differences
			// and likes.
			post.setCreatedTime(p.getCreatedTime());
			post.setLastUpdated(new Date());

			posts.add(post);

		}

		return posts;
	}

	@Override
	public List<SocialDistance> getSocialDistance(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

}
