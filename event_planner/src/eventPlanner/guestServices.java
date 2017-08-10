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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import eventDAO.HibernateUtil;
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
			for(java.util.Iterator<NotseatWith> i=guest.getNotseat().iterator(); i.hasNext();){
				System.out.println(i.next().getNotseatId());
			}
			
			System.out.println(guest.getNotseat().size());
			s.save(guest);
			
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			
			return Response.status(Response.Status.OK).build();
		}
		
	}
	
	
	@POST
	@Path("/import/{id}/file")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	@Consumes(MediaType.MULTIPART_FORM_DATA)

	public Response importGuestList(
			@PathParam("id") String id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("path") String path) {
		
		// Path format //IP/Installables/uploaded
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
               
               System.out.println(gsize-3);
               GuestInfo guestinf = new GuestInfo();
               guestinf.setEventId(Integer.parseInt(id));
  				guestinf.setGuestFname(guest[1]);
  				guestinf.setGuestLname(guest[2]);
  				guestinf.setGuestId(Integer.parseInt(guest[0]));
  				s.save(guestinf);
   			for(int i=3; i<gsize; i++){
   				
   				NotseatWith no = new NotseatWith();
   				SeatWith seat = new SeatWith();
   				if(i>(same+3)-1){
   					no.setGuestJd(Integer.parseInt(guest[0]));
   					no.setNotseatId(Integer.parseInt(guest[i]));
   					no.setEventId(42);
   					s.save(no);
   	   				
   				}else{
   					seat.setGuestId(Integer.parseInt(guest[0]));
   					seat.setSeatwithId(Integer.parseInt(guest[i]));
   					seat.setEventId(42);
   					s.save(seat);
   					
   				}
   				
   				if(count % 20 == 0){
   					s.flush();
   					s.clear();
   				}
   				
   			}
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
