package eventPlanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import algorithm.population;
import eventDAO.HibernateUtil;
import eventDAO.guestDAO;
import eventPD.GuestInfo;
import eventPD.NotseatWith;
import eventPD.SeatWith;
import javassist.bytecode.Descriptor.Iterator;

@Path("/guestservices")
public class guestServices {

	public guestServices() {


	}
	
	@POST
	@Path("/guest")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addguest(GuestInfo guest, @Context HttpServletResponse response){
		if(guest==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}else{
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			s.save(guest);
			
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			
			return Response.status(Response.Status.OK).build();
		}
		
	}
	
	
	@PUT
	@Path("/guest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateguest(GuestInfo guest, @Context HttpServletResponse response,@PathParam("id") String id){
		if(guest==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}else{
			guestDAO gu = new guestDAO();
			GuestInfo old = gu.guestSelected(Integer.parseInt(id));
			old.setGuestFname(guest.getGuestFname());
			old.setGuestLname(guest.getGuestLname());
			old.setNotSeat(guest.getNotSeat());
			old.setSeatWith(guest.getSeatWith());
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
	
	@GET
	@Path("/guestinfo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GuestInfo getguestinfo(@PathParam("id") String id){
			guestDAO gu = new guestDAO();
			return gu.guestSelected(Integer.parseInt(id));
		
	}
	
	
	@DELETE
	@Path("/deleteguest/{id}")
	public Response deleteAll(@PathParam("id") String id){
		try{
			guestDAO gu = new guestDAO();
			if(gu.guestCheck(Integer.parseInt(id))){
				gu.deleteAllGuest(Integer.parseInt(id));
				}
			return Response.status(Response.Status.OK).build();
		}catch(Exception e){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@DELETE
	@Path("/deleteselectedguest/{id}")
	public Response deleteselectedGuest(@PathParam("id") String id){
		try{
			guestDAO gu = new guestDAO();
			
				gu.deleteSelected(Integer.parseInt(id));
			return Response.status(Response.Status.OK).build();
		}catch(Exception e){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/guest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<GuestInfo> getAllGuest(@PathParam("id") String id){
		return guestDAO.getAllGuest(Integer.parseInt(id));
	}
	
	@GET
	@Path("/guestbytable/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<GuestInfo> getAllGuestbytable(@PathParam("id") String id){
		return guestDAO.getAllGuestByTable(Integer.parseInt(id));
	}
	
	@GET
	@Path("/guestnot")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<NotseatWith> getAllGuestnot(@QueryParam("event_id") String eventId,
			@QueryParam("guest_id") String guestId){
		return guestDAO.getAllGuestnot(Integer.parseInt(eventId),Integer.parseInt(guestId) );
	}
	
	@GET
	@Path("/guestseat")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SeatWith> getAllGuestseat(@QueryParam("event_id") String eventId,
			@QueryParam("guest_id") String guestId){
		return guestDAO.getAllGuestseat(Integer.parseInt(eventId),Integer.parseInt(guestId) );
	}
	
	@PUT
	@Path("/switchseat")
	public void swithchSeat(@QueryParam("guest_id") String guest_id,
			@QueryParam("switch_id") String switch_id){
		guestDAO gu = new guestDAO();
		GuestInfo old1 = gu.guestSelected(Integer.parseInt(guest_id));
		GuestInfo old2 = gu.guestSelected(Integer.parseInt(switch_id));
		int table = old1.getTableAssigned();
		old1.setTableAssigned(old2.getTableAssigned());
		old2.setTableAssigned(table);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(old1);
		s.update(old2);
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test(@QueryParam("event_id") String eventId,
			@QueryParam("guest_id") String guestId){
		population pop = new population();
		pop.generatePopulation(42);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/import/{id}")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	@Consumes(MediaType.MULTIPART_FORM_DATA)

	public Response importGuestList(
			@PathParam("id") String id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("path") String path) {
		
		// Path format //IP/Installables/uploaded
		guestDAO gu = new guestDAO();
		
		if(gu.guestCheck(Integer.parseInt(id))){
		gu.deleteAllGuest(Integer.parseInt(id));
		}
		
		
		System.out.println("path::"+path);
		System.out.println(uploadedInputStream.toString());
		System.out.println(fileDetail.toString());
		
		String uploadedFileLocation = path + fileDetail.getFileName();

// save it off to log that the file made it to Tomcat
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;
		String line = "";
		String splitLine = ",";
		
		try (BufferedReader br = new BufferedReader(new FileReader(path + fileDetail.getFileName()))) {
			String heading = br.readLine();
			String[] guest_heading = heading.split(splitLine);
			int size = guest_heading.length;
            int same = 0, notsame = 0;
            if(size==4){
            	same =1;
            }else if(size==6){
            	same = 2;
            	notsame = 1;
            }else if(size == 7){
            	same = 2;
            	notsame = 2;
            }else if(size == 8){
            	same = 3;
            	notsame = 2;
            }
            
            System.out.println(same + "" + notsame);
            
            int count = 0;
            SessionFactory sf = HibernateUtil.getSessionFactory();
   			Session s = sf.openSession();
   			Transaction tx = s.beginTransaction();
            while ((line = br.readLine()) != null) {
            	count++;
                // use comma as separator
                String[] guest = line.split(splitLine);
               int gsize = guest.length; 
               String seat = "";
               String seatnot = "";
               GuestInfo guestinf = new GuestInfo();
               guestinf.setEventId(Integer.parseInt(id));
  				guestinf.setGuestFname(guest[1]);
  				guestinf.setGuestLname(guest[2]);
  				guestinf.setGuestId(Integer.parseInt(guest[0]+id));
  				int cs = 0, cn = 0;
	   			for(int i=3; i<gsize; i++){
	   				
	   				if(i>(same+3)-1){
	   					if(cn==0){
	   						seatnot = guest[i]+id;
	   					}else{
	   						seatnot = seatnot + "," + guest[i]+id;
	   					}
	   					cn++;
	   				}else{
	   					if(cs==0){
	   						seat = guest[i]+id;
	   					}else{
	   						seat = seat + "," + guest[i]+id;
	   					}
	   					cs++;
	   				}	
	   			}
	   			guestinf.setNotSeat(seatnot);
	   			guestinf.setSeatWith(seat);
	   			s.save(guestinf);
  	            s.flush();
  				s.clear();
            }
            
            tx.commit();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

		
		
		return Response.status(200).entity(output).build();
	}
	
	/**
	 *Helper method that writes the received guest list out to a file 
	 *for logging purposes
	 *
	 *@paramuploadedInputStream - the bytes that were in the file
	 *@paramuploadedFileLocation - the place to save the file
	 */
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	

}
