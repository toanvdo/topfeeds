package au.edu.unsw.cse.topfeeds.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;


@Path("/account")
public class Account {

	@Context
	UriInfo uriInfo;	
	@Context
	Request request;

	private AccountDAOImpl acctDAO = new AccountDAOImpl();

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//accepts Username and Password and/or mac of client
	public void createAccount(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("mac") String mac,
			@FormParam("mac") String email,
			
			@Context HttpServletResponse servletResponse
			) throws IOException{
			servletResponse.sendRedirect("accountPage.jsp");
		// check if account exist or not then try to push it into db.
			TopFeedsUser tfu = new TopFeedsUser();
			tfu.setUsername(username);
			tfu.setPassword(password);
			tfu.setMac(mac);
			tfu.setEmail(email);
			try {
				acctDAO.registerTopFeedsUser(tfu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//redirect to error page...
				e.printStackTrace();
			}
	}
	
	@POST
	@Path("registerAccount/fb")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//registers a API key and service with account, requires username and api key and service type
	//kick off initial setup
	public void registerAccountFb(
		@FormParam("username") String username,
		@FormParam("password") String password,
		@FormParam("mac") String mac,
		@Context HttpServletResponse servletResponse
		) throws IOException{
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	//accepts username and email
	//sends email to user with password
	public void forgetAccountPassword(){
		//check grab password and email to user
	}
}
