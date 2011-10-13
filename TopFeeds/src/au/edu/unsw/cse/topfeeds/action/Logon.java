package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

/**
 * Servlet implementation class Logon
 */
public class Logon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logon() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		AccountDAO acctDao = new AccountDAOImpl();
		String errorMsg = "";
		String destination = "/menu.jsp";

		try {
			TopFeedsUser tfu = acctDao.getTopfeedsUser(username, password);
			if (tfu == null) {
				errorMsg = "Incorrect login.";
				destination = "/index.jsp";
			} else {
			    HttpSession session = request.getSession();
			    session.setAttribute("user", tfu);
			}
		} catch (Exception e) {
			errorMsg = "Incorrect login.";
			destination = "/index.jsp";
		}
		
		request.setAttribute("error", errorMsg);
		getServletContext().getRequestDispatcher(destination).forward(request,
				response);

	}
}
