package eventDAO;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import eventPD.EventInfo;
import eventPD.GuestInfo;
import eventPD.NotseatWith;
import eventPD.SeatWith;

public class guestDAO {

	public guestDAO() {
		// TODO Auto-generated constructor stub
		
	}

	public Boolean guestCheck(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<GuestInfo> eventinf =(ArrayList<GuestInfo>) s.createQuery("from GuestInfo WHERE eventId=?").setParameter(0, id).setMaxResults(5).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		if(eventinf.size()>1){
			System.out.println("true guest");
			return true;
		}else{
			System.out.println("false guest");
			return false;
		}
	}
	
	public GuestInfo guestSelected(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<GuestInfo> eventinf =(ArrayList<GuestInfo>) s.createQuery("from GuestInfo WHERE guestId=?").setParameter(0, id).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf.get(0);
	}
	
	
	public Boolean seatwithCheck(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<SeatWith> eventinf =(ArrayList<SeatWith>) s.createQuery("from SeatWith WHERE eventId=?").setParameter(0, id).setFirstResult(0).setMaxResults(5).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		if(eventinf.size()>1){
			System.out.println("true seat");
			return true;
		}else{
			System.out.println("true seat");
			return false;
		}
	}
	
	public Boolean notseatCheck(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<NotseatWith> eventinf =(ArrayList<NotseatWith>) s.createQuery("from NotseatWith WHERE eventId=?").setParameter(0, id).setFirstResult(0).setMaxResults(5).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		if(eventinf.size()>1){
			System.out.println("true notseat");
			return true;
		}else{
			System.out.println("false notseat");
			return false;
		}
	}
	
	
	public void deleteAllGuest(int eventid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("DELETE FROM GuestInfo WHERE eventId=?").setParameter(0, eventid);
		q.executeUpdate();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		deleteAllseat(eventid);
		deleteAllnot(eventid);
	}
	
	public void deleteSelected(int guestid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("DELETE FROM GuestInfo WHERE guestId=?").setParameter(0, guestid);
		q.executeUpdate();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
//		deleteSelectedseat(guestid);
//		deleteSelectednot(guestid);
	}
	
	public void deleteAllnot(int eventid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		if(notseatCheck(eventid)){
		Query q = s.createQuery("DELETE FROM NotseatWith WHERE eventId=?").setParameter(0, eventid);
		q.executeUpdate();
		}
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}
	
	public void deleteSelectednot(int guestid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Query q = s.createQuery("DELETE FROM NotseatWith WHERE guestJd=?").setParameter(0, guestid);
		q.executeUpdate();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}
	
	public void deleteAllseat(int eventid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		if(seatwithCheck(eventid)){
		Query q = s.createQuery("DELETE from SeatWith WHERE eventId=?").setParameter(0, eventid);
		q.executeUpdate();
		}
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}
	
	public void deleteSelectedseat(int guestid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Query q = s.createQuery("DELETE from SeatWith WHERE guestId=?").setParameter(0, guestid);
		q.executeUpdate();

		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}
	
	public static ArrayList<GuestInfo> getAllGuest(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		ArrayList<GuestInfo> eventinf =(ArrayList<GuestInfo>) s.createQuery("from GuestInfo WHERE eventId=? ORDER BY guestFname ASC").setParameter(0, id).list();
		
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	public static ArrayList<GuestInfo> getAllGuestByTable(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		ArrayList<GuestInfo> eventinf =(ArrayList<GuestInfo>) s.createQuery("from GuestInfo WHERE eventId=? ORDER BY tableAssigned ASC").setParameter(0, id).list();
		
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	public static ArrayList<NotseatWith> getAllGuestnot(int eventid, int guestid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		ArrayList<NotseatWith> eventinf =(ArrayList<NotseatWith>) s.createQuery("from NotseatWith WHERE eventId=? and guestJd =?")
				.setParameter(0, eventid)
				.setParameter(1, guestid)
				.list();
		
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	public static ArrayList<SeatWith> getAllGuestseat(int eventid, int guestid){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		ArrayList<SeatWith> eventinf =(ArrayList<SeatWith>) s.createQuery("from SeatWith WHERE eventId=? and guestId =?")
				.setParameter(0, eventid)
				.setParameter(1, guestid)
				.list();
		
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	
}
