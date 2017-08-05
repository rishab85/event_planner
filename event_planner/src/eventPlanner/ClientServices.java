package eventPlanner;

import java.io.IOException;
import java.util.ArrayList;

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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import eventDAO.HibernateUtil;
import eventDAO.clientDAO;
import eventPD.ClientInfo;

@Path("/clientservices")
public class ClientServices {
	clientDAO client=new clientDAO();
	public ClientServices() {
		// TODO Auto-generated constructor stub
	}
	
	@Secured
	@GET
	@Path("/clients")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ClientInfo> getClients(){
		return clientDAO.getAllClient();
	}

	@Secured
	@GET
	@Path("/clients/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientInfo getClientById(@PathParam("id") String id){
		return clientDAO.getClientByID(Integer.parseInt(id));
	}
	
	@Secured
	@POST
	@Path("/clients")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClient(ClientInfo client, @Context HttpServletResponse response){
		if(client==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}else{
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			s.save(client);
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			
			return Response.status(Response.Status.OK).build();
		}
		
	}
	
	@Secured
	   @PUT
	   @Path("/clients/{id}")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response udpatedStudent(ClientInfo client,@PathParam("id") String id, @Context final HttpServletResponse response) throws IOException{
		 if(client==null){
			 return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		 }else{
			 ClientInfo old = clientDAO.getClientByID(Integer.parseInt(id));
			 old.setClientAddress(client.getClientAddress());
			 old.setClientEmail(client.getClientEmail());
			 old.setClientName(client.getClientName());
			 old.setClientPhone(client.getClientPhone());
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
}
