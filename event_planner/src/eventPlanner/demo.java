package eventPlanner;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import antlr.Token;
import systemDAO.UserProfileDAO;
import systemPD.UserProfile;


// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/demo")
public class demo {

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserProfile> getUsers(){
		 
		System.out.println(UserProfileDAO.getAllUser().get(0).getFullName());
		
		System.out.println( );
		return UserProfileDAO.getAllUser();
		
	}
	
	@GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUser(@PathParam("id") String id){
       UserProfile user = UserProfileDAO.getUserByID(Integer.parseInt(id));
      
       return user;
    }
	
}
