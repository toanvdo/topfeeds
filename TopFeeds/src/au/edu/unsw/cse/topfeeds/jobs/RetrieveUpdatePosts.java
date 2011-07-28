package au.edu.unsw.cse.topfeeds.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

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

public class RetrieveUpdatePosts implements Job {

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
				List<Post> posts = getPostFromCurrentUser(acct, feedDAO);
				
				//TODO: Batch insert/update
				feedDAO.updatePosts(posts);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Post> getPostFromCurrentUser(Account acct, FeedDAO feedDAO) {
		List<Post> posts = null;

		switch (acct.getType()) {
		case FACEBOOK:
			posts = facebookDAO.getUserFeed(acct);
			break;
		case TWITTER:
			posts = twitterDAO.getUserFeed(acct);
			break;
		}

		for (Post p : posts) {
			// get social connection between the 2
			SocialDistance sd=new SocialDistance();
			try {
				sd = feedDAO.getSocialDistance(acct.getUserId(), p.getSenderId());
				
				
				// calculate scores
				int score = calculateScore(p.getCreatedTime().getTime(),
						p.getLikes(), p.getComments(),
						0,0);
//						sd.getMutualFriends(), sd.getInteractions());
				
				p.setScore(score);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return posts;
	}

	private int calculateScore(long createdTime, int likes, int comments,
			int mutualFriends, int interactions) {
		// TODO Add time attribute

		return likes + comments + mutualFriends + interactions;
	}

}
