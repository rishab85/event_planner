package eventPlanner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import eventDAO.HibernateUtil;
import eventDAO.clientDAO;
import eventDAO.eventInfoDAO;
import eventPD.ClientInfo;
import eventPD.EventInfo;

@Path("/eventservices")
public class EventServices {

	public EventServices() {
		
	}
	eventInfoDAO eventinfo = new eventInfoDAO();

	@Secured
	@GET
	@Path("/eventinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EventInfo> getAllEvent(){
		return eventinfo.getAlleventinf();
	}
	
	@Secured
	@GET
	@Path("/eventinfo/working")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EventInfo> getAllWorkingEvent(@DefaultValue("0") @QueryParam("page") String page,
		    @DefaultValue("10") @QueryParam("per_page") String perPage){
		return eventinfo.getAllWorkingEventinf(Integer.parseInt(page), Integer.parseInt(perPage));
	}
	
	@Secured
	@GET
	@Path("/eventinfo/completed")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EventInfo> getAllCompletedEvent(@DefaultValue("0") @QueryParam("page") String page,
		    @DefaultValue("10") @QueryParam("per_page") String perPage){
		return eventinfo.getAllCompleteEventinf(Integer.parseInt(page), Integer.parseInt(perPage));
	}
	
	
	@Secured
	@GET
	@Path("/eventinfo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventInfo getEventbyid(@PathParam("id") String id){
		return eventInfoDAO.geteventinfByID(Integer.parseInt(id));
	}
	
	@Secured
	@GET
	@Path("/clientevent/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EventInfo> getEventbyClientId(@PathParam("id") String id){
		return eventInfoDAO.geteventinfByClientId(Integer.parseInt(id));
	}

	@Secured
	@POST
	@Path("/eventinfo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEvent(EventInfo event, @Context HttpServletResponse response){
		if(event==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}else{
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			s.save(event);
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			
			return Response.status(Response.Status.OK).build();
		}
		
	}
	
		@Secured
	   @PUT
	   @Path("/eventinfo/{id}")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response udpatedEvent(EventInfo event,@PathParam("id") String id, @Context final HttpServletResponse response) throws IOException{
		 if(event==null){
			 return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		 }else{
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 EventInfo old = eventInfoDAO.geteventinfByID(Integer.parseInt(id));
			 old.setEventDate(event.getEventDate());
			 old.setEventEmptytable(event.getEventEmptytable());
			 old.setEventGuestsize(event.getEventGuestsize());
			 old.setEventName(event.getEventName());
			 old.setEventTablesize(event.getEventTablesize());
			 old.setEventVenue(event.getEventVenue());
			 old.setClientId(event.getClientId());
			 old.setUserId(event.getUserId());
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
		
		
		@Secured
		   @PUT
		   @Path("/markcomplete/{id}")
		   public Response udpatedCompleted(@PathParam("id") String id, @Context final HttpServletResponse response) throws IOException{

				 EventInfo old = eventInfoDAO.geteventinfByID(Integer.parseInt(id));
				 
				 old.setEventCompleted(1);
					SessionFactory sf = HibernateUtil.getSessionFactory();
					Session s = sf.openSession();
					Transaction tx = s.beginTransaction();
					s.update(old);
					s.flush();
					s.clear();
					tx.commit();
					s.close();
			 return Response.status(Response.Status.OK).build();
			 
		 }
}
