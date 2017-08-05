package eventPlanner;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import eventDAO.HibernateUtil;
import eventDAO.clientDAO;
import eventPD.ClientInfo;
import eventPD.EventInfo;
import systemDAO.UserProfileDAO;
import systemPD.Token;
import systemPD.UserProfile;

@Path("/systemservices")
public class SystemService {

	
	
	public SystemService() {
		// TODO Auto-generated constructor stub
	}

	@Context
	  SecurityContext securityContext;
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(UserProfile user){
		
		try{
			authenticate(user.getUserName(), user.getUserPassword());
			
			String token = issueToken(user.getUserName());
			return Response.ok(token).build();
		}catch(Exception e){
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	public void authenticate(String username, String password) throws Exception{
		
		UserProfile user = UserProfileDAO.getUserByName(username);
		if(user == null) throw new Exception();
		
		if(!user.authorize(password)) throw new Exception();
		
		if(!user.authorizeStatus()) throw new Exception();
	}
	
	private String issueToken(String username){
		Token token = new Token(UserProfileDAO.getUserByName(username));
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(token);
		s.flush();
		tx.commit();
		s.close();
		return token.getToken();
	}
	
	@Secured
	@GET
    @Path("/users/current")
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUser(){
      String username = securityContext.getUserPrincipal().getName();
      UserProfile user = UserProfileDAO.getUserByName(username);
      return user;
    }
	
	@Secured
	@GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<UserProfile> getAllUser(){
      String username = securityContext.getUserPrincipal().getName();
      ArrayList<UserProfile> user = UserProfileDAO.getAllUser();
      return user;
    }
	
	@Secured
	@GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserById(@PathParam("id") String id){
      String username = securityContext.getUserPrincipal().getName();
      UserProfile user = UserProfileDAO.getUserByID(Integer.parseInt(id));
      return user;
    }
	
	@Secured
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClient(UserProfile user, @Context HttpServletResponse response){
		if(user==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}else{
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			s.save(user);
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			
			return Response.status(Response.Status.OK).build();
		}
		
	}
	
	@Secured
	   @PUT
	   @Path("/users/{id}")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response udpateUser(UserProfile user,@PathParam("id") String id, @Context final HttpServletResponse response) throws IOException{
		 if(user==null){
			 return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		 }else{
			 UserProfile old = UserProfileDAO.getUserByID(Integer.parseInt(id));
			 old.setFullName(user.getFullName());
			 old.setUserEmail(user.getUserEmail());
			 old.setUserName(user.getUserName());
			 old.setUserPassword(user.getUserPassword());
			 old.setUserPhone(user.getUserPhone());
			 old.setUserStatus(user.getUserStatus());
				SessionFactory sf = HibernateUtil.getSessionFactory();
				Session s = sf.openSession();
				Transaction tx = s.beginTransaction();
				s.update(old);
				s.flush();
				s.clear();
				tx.commit();
				s.close();
		 }
		 return Response.status(Response.Status.OK).build();
		 
	 }
	
	@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfile test() throws ParseException{
		EventInfo event = new EventInfo();
		event.setClientId(1);
		Date date = new Date();
		event.setEventDate(date);
		event.setEventEmptytable(2);
		event.setEventGuestsize(200);
		event.setEventName("asdfa");
		event.setEventTablesize(4);
		event.setEventVenue("asdf");
		event.setUserId(1);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(event);
		s.flush();
		tx.commit();
		s.close();
		
		return null;
		
	}
	
	
}
