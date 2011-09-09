package au.edu.unsw.cse.topfeeds.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.external.FacebookDataAccess;
import au.edu.unsw.cse.topfeeds.dao.external.SocialDataAccess;
import au.edu.unsw.cse.topfeeds.dao.external.TwitterDataAccess;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.dao.impl.FeedDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.SocialDistance;

public class UpdateSocialScores implements Job {

	private SocialDataAccess facebookDAO = new FacebookDataAccess();
	private SocialDataAccess twitterDAO = new TwitterDataAccess();
	private AccountDAO acctDAO = new AccountDAOImpl();
	private FeedDAO feedDAO = new FeedDAOImpl();

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			for (Account acct : acctDAO.getAllActiveAccounts()) {
				try {
					List<SocialDistance> sds = getSocialScoreForAccount(acct,
							feedDAO);

					feedDAO.updateSocialScore(sds);
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

	private List<SocialDistance> getSocialScoreForAccount(Account acct,
			FeedDAO feedDAO) {
		List<SocialDistance> sds = null;

		switch (acct.getType()) {
		case FACEBOOK:
			sds = facebookDAO.getSocialDistance(acct);
			break;
		case TWITTER:
			sds = twitterDAO.getSocialDistance(acct);
			break;
		}

		return sds;
	}

}
