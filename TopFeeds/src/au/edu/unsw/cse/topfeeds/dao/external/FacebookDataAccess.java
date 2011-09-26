package au.edu.unsw.cse.topfeeds.dao.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class FacebookDataAccess implements SocialDataAccess {

	private AccountDAO accountDAO = new AccountDAOImpl();
	private static final String FACEBOOK_APISECRET = "9f31c6bbd5695cfbb3a5afe400e5d7f3";
	private static final String FACEBOOK_APIID = "218926754801065";

	@Override
	public List<Post> getUserFeed(Account account) {
		String accesstoken = account.getAccessToken();

		FacebookClient facebookClient = new DefaultFacebookClient(accesstoken);

		Connection<com.restfb.types.Post> filteredFeed = facebookClient
				.fetchConnection("me/home", com.restfb.types.Post.class);
		ArrayList<Post> posts = new ArrayList<Post>();

		for (com.restfb.types.Post p : filteredFeed.getData()) {

			Post post = new Post();
			post.setPostId(p.getId());
			post.setOwnerId(account.getId());
			if (p.getMessage() == null || p.getMessage().isEmpty()) {
				if (p.getDescription() == null || p.getDescription().isEmpty()) {
					post.setMessage(p.getCaption());
				} else {
					post.setMessage(p.getDescription());
				}
			} else {
				post.setMessage(p.getMessage());
			}

			if (p.getLikesCount() != null) {
				post.setLikes(p.getLikesCount().intValue());
			} else {
				post.setLikes(0);
			}

			if (p.getActions() != null && !p.getActions().isEmpty()) {
				post.setUrl(p.getActions().get(0).getLink());
			}

			if (p.getComments() != null) {
				post.setComments(p.getComments().getCount().intValue());
			} else {
				post.setComments(0);
			}

			post.setType(p.getType());

			// Check for sender id and then if there isnt any create one...
			int senderId = -1;
			try {
				senderId = accountDAO.getSocialNetworkUser(p.getFrom().getId(),
						SocialNetwork.FACEBOOK);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (senderId == -1) {
				try {
					senderId = accountDAO.registerFriend(p.getFrom().getId(),
							SocialNetwork.FACEBOOK, p.getFrom().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			post.setSenderId(senderId);

			// get social score, combine social distance and time differences
			// and likes.
			post.setCreatedTime(new java.sql.Date(p.getCreatedTime().getTime()));
			posts.add(post);

		}

		return posts;
	}

	@Override
	public List<SocialDistance> getSocialDistance(Account account) {
		String accesstoken = account.getAccessToken();

		FacebookClient facebookClient = new DefaultFacebookClient(accesstoken);

		// get list of friends

		Connection<com.restfb.types.User> friends = facebookClient
				.fetchConnection("me/friends", com.restfb.types.User.class);
		List<SocialDistance> sds = new ArrayList<SocialDistance>();
		List<com.restfb.types.User> users = friends.getData();

		List<MutualFriend> mutualFriends = facebookClient.executeQuery(
				"SELECT uid1, uid2 FROM friend " + "WHERE uid1 IN "
						+ "(SELECT uid2 FROM friend WHERE uid1="
						+ account.getUsername() + ") " + "AND uid2 IN "
						+ "(SELECT uid2 FROM friend WHERE uid1="
						+ account.getUsername() + ")", MutualFriend.class);
		Map<String, Integer> mutualFriendsMap = new HashMap<String, Integer>();
		for (MutualFriend mf : mutualFriends) {
			Integer count = mutualFriendsMap.get(mf.uid1);
			if (count == null) {
				mutualFriendsMap.put(mf.uid1, 1);
			} else {
				mutualFriendsMap.put(mf.uid1, ++count);
			}
		}

		// friends posting to my wall
		List<Interaction> interactionsToMe = facebookClient
				.executeQuery(
						"SELECT source_id, actor_id, message from stream where source_id="
								+ account.getUsername()
								+ " AND actor_id in (SELECT uid2 from friend where uid1="
								+ account.getUsername() + ")",
						Interaction.class);

		Map<String, Integer> interactionsMap = new HashMap<String, Integer>();
		for (Interaction inter : interactionsToMe) {
			Integer count = interactionsMap.get(inter.actor_id);
			if (count == null) {
				interactionsMap.put(inter.actor_id, 1);
			} else {
				interactionsMap.put(inter.actor_id, ++count);
			}
		}

		for (com.restfb.types.User u : users) {
			SocialDistance sd = new SocialDistance();
			sd.setAccountId(account.getId());
			// insert friend....
			try {
				int friendId = accountDAO.getSocialNetworkUser(u.getId(),
						SocialNetwork.FACEBOOK);
				if (friendId == -1) {
					friendId = accountDAO.registerFriend(u.getId(),
							SocialNetwork.FACEBOOK, u.getName());
				}
				sd.setFriendId(friendId);
				Integer mutualFriendsCount = mutualFriendsMap.get(u.getId());
				sd.setMutualFriends(mutualFriendsCount == null ? 0
						: mutualFriendsCount);

				List<com.restfb.types.Post> interactions = facebookClient
						.executeQuery(
								"SELECT message from stream where actor_id="
										+ account.getUsername()
										+ " AND source_id=" + u.getId(),
								com.restfb.types.Post.class);
				Integer freq = interactionsMap.get(u.getId());

				sd.setInteractions(interactions.size()
						+ (freq == null ? 0 : freq));
				sds.add(sd);
			} catch (Exception e) {
				// Catch the occasion where we cannot access friends of user X
				e.printStackTrace();
			}
		}

		return sds;
	}

	public Account getUserAccount(Integer userId, String accessToken, String tokenSecret) {
		if (userId == null || userId < 1) {
			return null;
		}
		
		System.out.println(accessToken);
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

		User user = facebookClient.fetchObject("me", User.class);

		Account acct = null;
		if (user != null) {
			acct = new Account();
			acct.setUserId(userId);
			acct.setUsername(user.getId());
			acct.setAccessToken(accessToken);
			acct.setName(user.getUsername());
			acct.setType(SocialNetwork.FACEBOOK);
		}

		return acct;
	}

	public static class MutualFriend {
		@Facebook
		String uid1;
		@Facebook
		String uid2;

		@Override
		public String toString() {
			return String.format("%s - %s", uid1, uid2);
		}
	}

	public static class Interaction {
		@Facebook
		String source_id;
		@Facebook
		String actor_id;
		@Facebook
		String message;
	}

}
