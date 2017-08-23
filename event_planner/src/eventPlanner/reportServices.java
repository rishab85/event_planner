package eventPlanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.itextpdf.text.DocumentException;

import eventDAO.guestDAO;
import eventDAO.reportsDAO;
import eventPD.SeatWith;

@Path("/reportservices")
public class reportServices {

	public reportServices() {
		// TODO Auto-generated constructor stub
	}

	private static final String FILE_PATH = "C:\\Users\\risha\\Downloads\\eclipse-java-mars-2-win32-x86_64\\eclipse\\guestlist.pdf";
	@GET
	@Path("/report/{id}")
	@Produces("application/pdf")
	public Response getAllGuestseat(@PathParam("id") String id) throws FileNotFoundException, DocumentException{
		reportsDAO report = new reportsDAO();
		report.createPDF(Integer.parseInt(id));
		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=report-by-person.pdf");
		return response.build();
	}
	
	private static final String FILE_PATH_BYTABLE = "C:\\Users\\risha\\Downloads\\guestlistbytable.pdf";
	@GET
	@Path("/reportbytable/{id}")
	@Produces("application/pdf")
	public Response getAllGuestByTable(@PathParam("id") String id) throws FileNotFoundException, DocumentException{
		reportsDAO report = new reportsDAO();
		report.createPDFByTable(Integer.parseInt(id));
		File file = new File(FILE_PATH_BYTABLE);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=report-by-table.pdf");
		return response.build();
	}
	
	private static final String FILE_PATH_PLACECARD = "C:\\Users\\risha\\Downloads\\placecard.pdf";
	@GET
	@Path("/placecard/{id}")
	@Produces("application/pdf")
	public Response getAllPlacecard(@PathParam("id") String id) throws FileNotFoundException, DocumentException{
		reportsDAO report = new reportsDAO();
		report.createPlacecard(Integer.parseInt(id));;
		File file = new File(FILE_PATH_PLACECARD);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=placecard.pdf");
		return response.build();
	}
}
