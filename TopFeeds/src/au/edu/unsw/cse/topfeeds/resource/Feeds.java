package au.edu.unsw.cse.topfeeds.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/feeds")
public class Feeds {
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	//accepts username and mac
	public void getFeeds(){
		//return user feeds
	}
}
