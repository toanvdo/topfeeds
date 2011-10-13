package au.edu.unsw.cse.topfeeds.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import au.edu.unsw.cse.topfeeds.dao.FeedDAO;
import au.edu.unsw.cse.topfeeds.dao.impl.FeedDAOImpl;
import au.edu.unsw.cse.topfeeds.model.Post;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

/**
 * Servlet implementation class GetFeeds
 */
public class GetFeeds extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GetFeeds.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetFeeds() {
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
		String pageString = request.getParameter("page");
		boolean ranked = "true".equals(request.getParameter("ranked"));

		Integer page = Integer.parseInt(pageString == null ? "0" : pageString);

		String errorMsg = "";
		String destination = "/feeds.jsp";
		try {
			if (tfu == null) {
				destination = "/index.jsp";
			} else {
				FeedDAO feedDao = new FeedDAOImpl();
				List<Post> posts;
				if (ranked) {
					posts = feedDao.retrieveFeedRanked(tfu.getId(),
							page == null ? 0 : page);
				} else {
					posts= feedDao.retrieveFeedUnranked(tfu.getId(),
							page == null ? 0 : page);

				}
				request.setAttribute("type", ranked?"Ranked":"Most Recent");

				request.setAttribute("posts", posts);
			}
		} catch (Exception e) {
			log.error("Cannot Retrieve Feeds", e);
			errorMsg = "Cannot Retrieve Feeds";
		}
		if (request.getAttribute("error") == null || !errorMsg.isEmpty()) {
			request.setAttribute("error", errorMsg);
		}
		getServletContext().getRequestDispatcher(destination).forward(request,
				response);

	}

}
