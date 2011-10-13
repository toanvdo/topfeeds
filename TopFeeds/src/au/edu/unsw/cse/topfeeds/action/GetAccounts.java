package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

/**
 * Servlet implementation class Accounts
 */
public class GetAccounts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GetAccounts.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAccounts() {
		super();
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
	    TopFeedsUser tfu = (TopFeedsUser)session.getAttribute("user");
	    
		
		String errorMsg = "";
		String destination ="/accounts.jsp";
		try {
			if (tfu == null){
				destination ="/index.jsp";
			}else{
				AccountDAO acctDao = new AccountDAOImpl();
				List<Account> accts = acctDao.getAccount(tfu.getId());
				request.setAttribute("accounts", accts);
			}
		} catch (Exception e) {
			log.error("Cannot Retrieve Accounts",e);
			errorMsg = "Cannot Retrieve Accounts";
		}
		if (request.getAttribute("error")==null || !errorMsg.isEmpty()){
			request.setAttribute("error", errorMsg);
		}
		getServletContext().getRequestDispatcher(destination).forward(request,
				response);


	}
}
