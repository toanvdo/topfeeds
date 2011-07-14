package au.edu.unsw.cse.topfeeds.resource;

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


@Path("/account")
public class Account {

	@Context
	UriInfo uriInfo;	
	@Context
	Request request;

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//accepts Username and Password and/or mac of client
	public void createAccount(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("mac") String mac,
			@Context HttpServletResponse servletResponse
			){
		
		// check if account exist or not then try to push it into db.
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	//registers a API key and service with account, requires username and api key and service type
	//kick off initial setup
	public void registerAccount(){
		
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
