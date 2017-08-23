package eventDAO;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import eventPD.ClientInfo;
import eventPD.EventInfo;
import eventPD.GuestInfo;

public class reportsDAO {

	public reportsDAO() throws DocumentException, FileNotFoundException {	
		 
	}

	public void createPDF(int eventid) throws FileNotFoundException, DocumentException{
		eventInfoDAO event = new eventInfoDAO();
		EventInfo ef = event.geteventinfByID(eventid);
		
		guestDAO guest = new guestDAO();
		clientDAO cl = new clientDAO();
		ClientInfo cinfo = cl.getClientByID(ef.getClientId());
		ArrayList<GuestInfo> glist = guest.getAllGuest(eventid);
		com.itextpdf.text.Font fontbold = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
		com.itextpdf.text.Font regular = FontFactory.getFont("Arial", 10, Font.PLAIN);
		com.itextpdf.text.Font header = FontFactory.getFont("Arial", 10, Font.BOLD);
		CMYKColor cmyk = new CMYKColor(5, 3, 0, 40);
		CMYKColor onT = new CMYKColor(64, 0, 74, 9);
		CMYKColor la = new CMYKColor(0, 29, 29, 4);
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("guestlist.pdf"));
		doc.open();
        // step 2
		doc.add(new Paragraph("Client Name : " + cinfo.getClientName(), fontbold));
		doc.add(new Paragraph("Event Name : " + ef.getEventName(), header));
		doc.add(new Paragraph("Event Date : " + ef.getEventDate(), regular));
		doc.add(new Paragraph("Total Guest : " + glist.size(), regular));
		
		doc.add(new Paragraph(" "));
		PdfPTable tbl = new PdfPTable(2);
		
		PdfPCell head1 = new PdfPCell(new Phrase("Guest Name",header)); 
		head1.setPadding(5);
		head1.setBackgroundColor(cmyk);
		tbl.addCell(head1);
		tbl.setHeaderRows(0);
		
		PdfPCell head2 = new PdfPCell(new Phrase("Table Number",header)); 
		head2.setPadding(5);
		head2.setBackgroundColor(cmyk);
		tbl.addCell(head2);
		for(int i=0; i<glist.size(); i++){
			PdfPCell myCell1 = new PdfPCell(new Phrase(glist.get(i).getGuestFname() + " " + glist.get(i).getGuestLname(), regular));
			myCell1.setPadding(10);
			tbl.addCell(myCell1);
			
			PdfPCell myCell2 = new PdfPCell(new Phrase(String.valueOf(glist.get(i).getTableAssigned()), regular));
			myCell2.setPadding(10);
			tbl.addCell(myCell2);
		}
        // step 3
        // step 5
		doc.add(tbl);
		doc.close();
	}
	
	public void createPDFByTable(int eventid) throws FileNotFoundException, DocumentException{
		eventInfoDAO event = new eventInfoDAO();
		EventInfo ef = event.geteventinfByID(eventid);
		
		guestDAO guest = new guestDAO();
		clientDAO cl = new clientDAO();
		ClientInfo cinfo = cl.getClientByID(ef.getClientId());
		ArrayList<GuestInfo> glist = guest.getAllGuestByTable(eventid);
		com.itextpdf.text.Font fontbold = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
		com.itextpdf.text.Font regular = FontFactory.getFont("Arial", 10, Font.PLAIN);
		com.itextpdf.text.Font header = FontFactory.getFont("Arial", 10, Font.BOLD);
		CMYKColor cmyk = new CMYKColor(5, 3, 0, 40);
		CMYKColor onT = new CMYKColor(64, 0, 74, 9);
		CMYKColor la = new CMYKColor(0, 29, 29, 4);
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\risha\\Downloads\\guestlistbytable.pdf"));
		doc.open();
        // step 2
		doc.add(new Paragraph("Client Name : " + cinfo.getClientName(), fontbold));
		doc.add(new Paragraph("Event Name : " + ef.getEventName(), header));
		doc.add(new Paragraph("Event Date : " + ef.getEventDate(), regular));
		doc.add(new Paragraph("Total Guest : " + glist.size(), regular));
		
		doc.add(new Paragraph(" "));
		PdfPTable tbl = new PdfPTable(2);
		
		PdfPCell head1 = new PdfPCell(new Phrase("Guest Name",header)); 
		head1.setPadding(10);
		head1.setBackgroundColor(cmyk);
		tbl.addCell(head1);
		tbl.setHeaderRows(0);
		
		PdfPCell head2 = new PdfPCell(new Phrase("Table Number",header)); 
		head2.setPadding(10);
		head2.setBackgroundColor(cmyk);
		tbl.addCell(head2);
		for(int i=0; i<glist.size(); i++){
			PdfPCell myCell1 = new PdfPCell(new Phrase(glist.get(i).getGuestFname() + " " + glist.get(i).getGuestLname(), regular));
			myCell1.setPadding(10);
			myCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl.addCell(myCell1);
			
			PdfPCell myCell2 = new PdfPCell(new Phrase(String.valueOf(glist.get(i).getTableAssigned()), regular));
			myCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			myCell2.setPadding(10);
			tbl.addCell(myCell2);
		}
        // step 3
        // step 5
		doc.add(tbl);
		doc.close();
	}
	
	public void createPlacecard(int eventid) throws FileNotFoundException, DocumentException{
		eventInfoDAO event = new eventInfoDAO();
		EventInfo ef = event.geteventinfByID(eventid);
		
		guestDAO guest = new guestDAO();
		clientDAO cl = new clientDAO();
		ClientInfo cinfo = cl.getClientByID(ef.getClientId());
		ArrayList<GuestInfo> glist = guest.getAllGuestByTable(eventid);
		com.itextpdf.text.Font fontbold = FontFactory.getFont("Times-Roman", 18, Font.PLAIN);
		com.itextpdf.text.Font regular = FontFactory.getFont("Arial", 10, Font.PLAIN);
		com.itextpdf.text.Font header = FontFactory.getFont("Arial", 10, Font.BOLD);
		CMYKColor cmyk = new CMYKColor(5, 3, 0, 40);
		CMYKColor onT = new CMYKColor(64, 0, 74, 9);
		CMYKColor la = new CMYKColor(0, 29, 29, 4);
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\risha\\Downloads\\placecard.pdf"));
		doc.open();
        // step 2
		doc.add(new Paragraph("Client Name : " + cinfo.getClientName(), fontbold));
		doc.add(new Paragraph("Event Name : " + ef.getEventName(), header));
		doc.add(new Paragraph("Event Date : " + ef.getEventDate(), regular));
		doc.add(new Paragraph("Total Guest : " + glist.size(), regular));
		
		doc.add(new Paragraph(" "));
		PdfPTable tbl = new PdfPTable(2);
		
		PdfPCell head1 = new PdfPCell(new Phrase("Guest Name",header)); 
		int gsize = glist.size();
		for(int i=0; i<glist.size(); i++){
			PdfPCell cell = new PdfPCell();
			
			Image image = null;
			try {
				image = Image.getInstance("C:\\Users\\risha\\Desktop\\border.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			cell.setImage(image);
			
			
			int currenttable = glist.get(i).getTableAssigned();
			cell.addElement(new Phrase("   "+" Table " + String.valueOf(currenttable), fontbold));
			cell.addElement(new Phrase("       "));
			if(gsize>=ef.getEventTablesize()){
			for(int j=i; j<i+ef.getEventTablesize(); j++){
				
				if(glist.get(j).getTableAssigned()==currenttable){
					cell.addElement(new Phrase("       "+glist.get(j).getGuestFname() + " " + glist.get(j).getGuestLname(), regular));
		
					cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
					i++;
					gsize--;
					System.out.println(i);
				}else{
					currenttable = glist.get(j).getTableAssigned();
					i = i -1;
					break;
				}
			}
			}else{
				for(int l = i; l<i+gsize; l++){
					cell.addElement(new Phrase("       "+glist.get(l).getGuestFname() + " " + glist.get(l).getGuestLname(), regular));
					i++;
					System.out.println(i);
					gsize--;
				}
			}
			cell.setPadding(10);
			cell.setCellEvent(new ImageBackgroundEvent(image));
			cell.setFixedHeight(230);
			tbl.addCell(cell);
		}
        // step 3
        // step 5
		doc.add(tbl);
		doc.close();
	}
	
	
}
