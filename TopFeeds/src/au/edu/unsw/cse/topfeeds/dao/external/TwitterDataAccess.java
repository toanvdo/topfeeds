package au.edu.unsw.cse.topfeeds.dao.external;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class TwitterDataAccess implements SocialDataAccess {
	private static final String TWITTER_CONSUMER_KEY = "cDsh3nnEmQFdAvSJf7fGQ";
	private static final String TWITTER_CONSUMER_SECRET = "4EsXxsTm7NdQHxoMTxWWc77HyW1Ww0KYp2kPehHc";

	private AccountDAO accountDAO = new AccountDAOImpl();

	public List<Post> getUserFeed(Account account) {

		AccessToken at = new AccessToken(account.getAccessToken(),
				account.getTokenSecret());

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(at);
		// gets Twitter instance with default credentials
		List<Post> posts = new ArrayList<Post>();
		try {
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();

			for (Status status : statuses) {
				Post post = new Post();
				post.setPostId(status.getId() + "");
				post.setOwnerId(account.getUserId());

				post.setMessage(status.getText());

				if ((int) status.getRetweetCount() >= 0) {
					post.setLikes((int) status.getRetweetCount());
				}

				// post.setUrl();
				if (status.getContributors() != null) {
					post.setComments(status.getContributors().length);
				}

				post.setType("NORMAL");

				int senderId = -1;
				try {
					senderId = accountDAO.getSocialNetworkUser(status.getUser()
							.getId() + "", SocialNetwork.TWITTER);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (senderId == -1) {
					try {
						senderId = accountDAO.registerFriend(status.getUser()
								.getId() + "", SocialNetwork.TWITTER, status
								.getUser().getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				post.setSenderId(senderId);
				post.setCreatedTime(new java.sql.Date(status.getCreatedAt()
						.getTime()));

				posts.add(post);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posts;
	}

	public List<SocialDistance> getSocialDistance(Account account) {
		AccessToken at = new AccessToken(account.getAccessToken(),
				account.getTokenSecret());
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(at);
		// gets Twitter instance with default credentials
		List<SocialDistance> sds = new ArrayList<SocialDistance>();

		try {
			User user = twitter.verifyCredentials();

			Map<Long, Integer> interactionsMap = new HashMap<Long, Integer>();

			// from other user to this account
			ResponseList<Status> mentionsList = twitter.getMentions();

			for (Status s : mentionsList) {
				Integer count = interactionsMap.get(s.getUser().getId());
				if (count == null) {
					interactionsMap.put(s.getUser().getId(), 1);
				} else {
					interactionsMap.put(s.getUser().getId(), ++count);
				}
			}

			// direct interactions count
			ResponseList<DirectMessage> directMessagesList = twitter
					.getDirectMessages();

			for (DirectMessage dm : directMessagesList) {
				long userId = dm.getRecipientId();

				if (userId == user.getId()) {
					userId = dm.getSenderId();
				}

				Integer count = interactionsMap.get(userId);

				if (count == null) {
					interactionsMap.put(userId, 1);
				} else {
					interactionsMap.put(userId, ++count);
				}
			}

			// Retweets...
			ResponseList<Status> retweetList = twitter.getRetweetedByMe();

			for (Status s : retweetList) {
				Integer count = interactionsMap.get(s.getUser().getId());
				if (count == null) {
					interactionsMap.put(s.getUser().getId(), 1);
				} else {
					interactionsMap.put(s.getUser().getId(), ++count);
				}
			}

			// Get interactions
			// Search for user and plus one... using @mentions
			try {
				ResponseList<Status> friendTimeline = twitter.getUserTimeline(
						user.getId(), new Paging(1, 1000));

				for (Status s : friendTimeline) {
					Integer count = interactionsMap.get(s.getUser().getId());
					if (count == null) {
						interactionsMap.put(s.getUser().getId(), 1);
					} else {
						interactionsMap.put(s.getUser().getId(), ++count);
					}
				}
			} catch (TwitterException te) {
				te.printStackTrace();

			}

			Set<Long> friendsIds = getFriendsIDs(twitter, user.getId());
			
			for (Long friendId : friendsIds) {
				Set<Long> friendsList = getFriendsIDs(twitter, friendId);
				
				//TODO: consider removing as uses too many api calls.
				User friend = twitter.showUser(friendId);
				int mutualCount = 0;
				SocialDistance sd = new SocialDistance();
				sd.setAccountId(account.getId());

				try {
					int friendAccountId = accountDAO.getSocialNetworkUser(
							friendId + "", SocialNetwork.TWITTER);
					if (friendAccountId == -1) {
						friendAccountId = accountDAO.registerFriend(
								friendId + "", SocialNetwork.TWITTER,
								friend.getName());
					}

					sd.setFriendId(friendAccountId);

					for (Long x : friendsIds) {
						if (friendsList.contains(x)) {
							mutualCount++;
						}

					}
					sd.setMutualFriends(mutualCount);
					Integer interactionCount = interactionsMap
							.get(friendId);
					if (interactionCount != null) {
						sd.setInteractions(interactionCount);
					} else {
						sd.setInteractions(0);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sds.add(sd);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sds;
	}

	private Set<Long> getFriendsIDs(Twitter twitter, long userId)
			throws TwitterException {
		long cursor = -1;
		Set<Long> friendsIds = new HashSet<Long>();
//		while (cursor != 0) {
//			IDs followersId = twitter.getFollowersIDs(userId, cursor);
//
//			Long[] longObjects = ArrayUtils.toObject(followersId.getIDs());
//			friendsIds.addAll(Arrays.asList(longObjects));
//			cursor = followersId.getNextCursor();
//		}
		cursor=-1;
		while (cursor != 0) {
			IDs friendsId = twitter.getFriendsIDs(userId, cursor);

			Long[] longObjects = ArrayUtils.toObject(friendsId.getIDs());
			friendsIds.addAll(Arrays.asList(longObjects));
			cursor = friendsId.getNextCursor();
		}
		return friendsIds;
	}
}
