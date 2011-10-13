package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;
import au.edu.unsw.cse.topfeeds.model.UserPreference;

/**
 * Servlet implementation class GetUserPreference
 */
public class GetUserPreference extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GetUserPreference.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserPreference() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    TopFeedsUser tfu = (TopFeedsUser)session.getAttribute("user");
	    
		
		String errorMsg = "";
		String destination ="/userPref.jsp";
		try {
			if (tfu == null){
				destination ="/index.jsp";
			}else{
				AccountDAO acctDao = new AccountDAOImpl();
				UserPreference userPref = acctDao.getUserPreference(tfu.getId());
				request.setAttribute("userPref", userPref);
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
