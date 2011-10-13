package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.external.SocialDataAccess;
import au.edu.unsw.cse.topfeeds.dao.external.TwitterDataAccess;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.jobs.InitialSetup;
import au.edu.unsw.cse.topfeeds.jobs.RetrieveUpdatePosts;
import au.edu.unsw.cse.topfeeds.jobs.UpdateSocialScores;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

/**
 * Servlet implementation class AddTwitter
 */
public class AddTwitter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(AddTwitter.class);

	private static final String TWITTER_CONSUMER_KEY = "cDsh3nnEmQFdAvSJf7fGQ";
	private static final String TWITTER_CONSUMER_SECRET = "4EsXxsTm7NdQHxoMTxWWc77HyW1Ww0KYp2kPehHc";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddTwitter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);

		TopFeedsUser tfu = (TopFeedsUser) request.getSession().getAttribute(
				"user");
		String destination = "/GetAccounts";
		if (tfu == null) {
			destination = "/index.jsp";
		} else {

			RequestToken requestToken = (RequestToken) request.getSession()
					.getAttribute("requestToken");
			String verifier = request.getParameter("oauth_verifier");
			String errorMsg = "";
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(
						requestToken, verifier);
				request.getSession().removeAttribute("requestToken");

				SocialDataAccess tda = new TwitterDataAccess();
				Account account = tda.getUserAccount(tfu.getId(),
						accessToken.getToken(), accessToken.getTokenSecret());
				if (account != null) {

					AccountDAO acctDao = new AccountDAOImpl();
					try {
						acctDao.registerAccount(account);
						Thread thread = new InitialSetup(account);
						thread.start();
						//TODO: some sort of message to tell user to wait for their accounts to load
					} catch (Exception e) {
						errorMsg = e.getMessage();
						log.error(e.getMessage());
					}

				} else {
					errorMsg = "Some wrong happened. Try again";
				}

			} catch (TwitterException e) {
				throw new ServletException(e);
			}
			request.setAttribute("error", errorMsg);
		}
		getServletContext().getRequestDispatcher(destination).forward(request,
				response);
	}
}
