package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;
import au.edu.unsw.cse.topfeeds.model.UserPreference;

/**
 * Servlet implementation class SaveUserPreference
 */
public class SaveUserPreference extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveUserPreference() {
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

		HttpSession session = request.getSession();
		TopFeedsUser tfu = (TopFeedsUser) session.getAttribute("user");

		float recencyPref = Float.parseFloat(request
				.getParameter("recencyPref"));
		float socialPref = Float.parseFloat(request.getParameter("socialPref"));

		float popularPref = Float.parseFloat(request.getParameter("popularPref"));

		String networkPref = request.getParameter("networkPref");
		SocialNetwork sn = null;
		if (!"NONE".equals(networkPref)) {
			sn = SocialNetwork.valueOf(networkPref);
		}

		UserPreference userPref = new UserPreference();
		userPref.setUserId(tfu.getId());
		userPref.setNetworkPref(sn);
		userPref.setPopularityPref(popularPref);
		userPref.setRecencyPref(recencyPref);
		userPref.setSocialDistancePref(1-popularPref);
		userPref.setAffinityPref(socialPref);
		userPref.setMutualFriendsPref(1-socialPref);


		AccountDAO acctDao = new AccountDAOImpl();
		acctDao.updateUserPreference(userPref);

		// kick off forced recalculate of scores...

		getServletContext().getRequestDispatcher("/menu.jsp").forward(request,
				response);

	}

}
