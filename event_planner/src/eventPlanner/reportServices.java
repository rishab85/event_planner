package eventPlanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
	@Path("/report")
	@Produces("application/pdf")
	public Response getAllGuestseat() throws FileNotFoundException, DocumentException{
		reportsDAO report = new reportsDAO();
		report.createPDF(42);
		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=new-android-book.pdf");
		return response.build();
	}
	
	private static final String FILE_PATH_BYTABLE = "C:\\Users\\risha\\Downloads\\guestlistbytable.pdf";
	@GET
	@Path("/reportbytable")
	@Produces("application/pdf")
	public Response getAllGuestByTable() throws FileNotFoundException, DocumentException{
		reportsDAO report = new reportsDAO();
		report.createPDFByTable(42);
		File file = new File(FILE_PATH_BYTABLE);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=new-android-book.pdf");
		return response.build();
	}
	
	private static final String FILE_PATH_PLACECARD = "C:\\Users\\risha\\Downloads\\placecard.pdf";
	@GET
	@Path("/placecard")
	@Produces("application/pdf")
	public Response getAllPlacecard() throws FileNotFoundException, DocumentException{
		reportsDAO report = new reportsDAO();
		report.createPlacecard(42);;
		File file = new File(FILE_PATH_PLACECARD);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=new-android-book.pdf");
		return response.build();
	}
}
