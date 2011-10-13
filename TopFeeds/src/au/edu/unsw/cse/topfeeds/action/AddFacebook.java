package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.external.FacebookDataAccess;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.jobs.InitialSetup;
import au.edu.unsw.cse.topfeeds.jobs.RetrieveUpdatePosts;
import au.edu.unsw.cse.topfeeds.jobs.UpdateSocialScores;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

/**
 * Servlet implementation class AddFacebook
 */
public class AddFacebook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(AddFacebook.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFacebook() {
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

		TopFeedsUser tfu = (TopFeedsUser) request.getSession().getAttribute(
				"user");
		String destination = "/GetAccounts";
		if (tfu == null) {
			destination = "/index.jsp";
		} else {

			String accessToken = request.getParameter("access_token");

			FacebookDataAccess fda = new FacebookDataAccess();

			Account account = fda.getUserAccount(tfu.getId(), accessToken, "");
			String errorMsg = "";
			if (account != null) {

				AccountDAO acctDao = new AccountDAOImpl();
				try {
					acctDao.registerAccount(account);
					Thread thread = new InitialSetup(account);
					thread.start();
				} catch (Exception e) {
					errorMsg = e.getMessage();
					log.error(e.getMessage());
				}

			} else {
				errorMsg = "Some wrong happened. Try again";
			}
			request.setAttribute("error", errorMsg);
		}
		getServletContext().getRequestDispatcher(destination).forward(
				request, response);

	}

}
