package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;
import au.edu.unsw.cse.topfeeds.model.UserPreference;

/**
 * Servlet implementation class RegisterUser
 */
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(RegisterUser.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");
		String name = request.getParameter("name");

		ArrayList<String> errors = new ArrayList<String>();
		if (username == null || username.isEmpty()) {
			errors.add("Invalid Username");
		}

		if (password == null || password.isEmpty()) {
			errors.add("Invalid Password");
		}

		if (password2 == null || password2.isEmpty()) {
			errors.add("Invalid Password");
		}

		if (password != null && password2 != null
				&& !password.equals(password2)) {
			errors.add("Passwords Mismatch");
		}

		if (email == null || password.isEmpty()) {
			errors.add("Invalid Email");
		}

		if (name == null || name.isEmpty()) {
			errors.add("Invalid Name");
		}

		AccountDAO acctDao = new AccountDAOImpl();

		TopFeedsUser tfu = new TopFeedsUser();
		tfu.setEmail(email);
		tfu.setPassword(password);
		tfu.setStatus("ACTIVE");
		tfu.setUsername(username);
		tfu.setName(name);

		String destination = "/menu.jsp";
		try {
			int userId = acctDao.registerTopFeedsUser(tfu);
			// Create default UserPreference
			UserPreference userPref = new UserPreference();
			userPref.setNetworkPref(null);
			userPref.setPopularityPref(0.5f);
			userPref.setRecencyPref(0.5f);
			userPref.setSocialDistancePref(0.5f);
			userPref.setAffinityPref(0.5f);

			userPref.setUserId(userId);
			acctDao.updateUserPreference(userPref);

			request.getSession().setAttribute("user", tfu);
		} catch (Exception e) {
			log.error("Register User error:" + e.getMessage());
			String errorMsg = "- Please check details";

			for (String error : errors) {
				errorMsg += "<br/>" + error;
			}

			request.setAttribute("error", errorMsg);

			destination = "/registerUser.jsp";
		}

		getServletContext().getRequestDispatcher(destination).forward(request,
				response);
	}

}
