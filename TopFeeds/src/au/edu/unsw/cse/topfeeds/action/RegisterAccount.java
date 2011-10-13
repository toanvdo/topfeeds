package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

/**
 * Servlet implementation class RegisterAccount
 */
public class RegisterAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(RegisterAccount.class);
	private static final String TWITTER_CONSUMER_KEY = "cDsh3nnEmQFdAvSJf7fGQ";
	private static final String TWITTER_CONSUMER_SECRET = "4EsXxsTm7NdQHxoMTxWWc77HyW1Ww0KYp2kPehHc";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		TopFeedsUser tfu = (TopFeedsUser) session.getAttribute("user");
		String destination = null;
		String errorMsg = "";

		if (tfu != null) {

			String type = request.getParameter("type");
			switch (SocialNetwork.valueOf(type)) {
			case FACEBOOK:
				destination = "https://www.facebook.com/dialog/permissions.request?app_id=218926754801065&display=page&next=http%3A%2F%2Flocalhost:8080%2FTopFeeds%2FcallbackFacebook.jsp?id="
						+ tfu.getId()
						+ "&response_type=token&fbconnect=1&perms=offline_access%2Cread_stream";
				break;
			case TWITTER:
	            RequestToken requestToken = getTwitterOauthUrl(tfu.getId());
	            request.getSession().setAttribute("requestToken", requestToken);
	            
	            destination =requestToken.getAuthenticationURL();
				break;
			}
			if (destination == null) {
				errorMsg = "Cannot process, try again later.";
			} else {
				response.sendRedirect(destination);
				return;
			}
		} else {
			destination = "/index.jsp";
		}

		request.setAttribute("error", errorMsg);
		getServletContext().getRequestDispatcher(destination).forward(request,
				response);
	}

	private RequestToken getTwitterOauthUrl(int userid) {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
		RequestToken requestToken;
		try {
			requestToken = twitter
					.getOAuthRequestToken();
			return requestToken ;
		} catch (TwitterException e) {
			log.error("Cannot get Twitter OAuth", e);
		}

		return null;

	}
}
