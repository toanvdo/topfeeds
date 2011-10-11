package au.edu.unsw.cse.topfeeds.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.dao.external.FacebookDataAccess;
import au.edu.unsw.cse.topfeeds.dao.external.SocialDataAccess;
import au.edu.unsw.cse.topfeeds.dao.external.TwitterDataAccess;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.dao.impl.FeedDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;
import au.edu.unsw.cse.topfeeds.model.UserPreference;

public class RetrieveUpdatePosts implements Job {

	private static final int MAX_CAP = 50;
	private static final float NETWORK_BONUS = 1.5f;
	private SocialDataAccess facebookDAO = new FacebookDataAccess();
	private SocialDataAccess twitterDAO = new TwitterDataAccess();
	private AccountDAO acctDAO = new AccountDAOImpl();
	private FeedDAO feedDAO = new FeedDAOImpl();

	/**
	 * @param args
	 */

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// retrieve accounts records which are active in status then call
		// appropriate external

		try {
			for (Account acct : acctDAO.getAllActiveAccounts()) {
				try {
					updatePostFromCurrentUser(acct);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatePostFromCurrentUser(Account acct) {
		List<Post> posts = null;
		switch (acct.getType()) {
		case FACEBOOK:
			posts = facebookDAO.getUserFeed(acct);
			break;
		case TWITTER:
			posts = twitterDAO.getUserFeed(acct);
			break;
		}

		feedDAO.updatePosts(posts);
	}

}
