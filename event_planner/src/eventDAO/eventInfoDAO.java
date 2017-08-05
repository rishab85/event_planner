package eventDAO;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import eventPD.EventInfo;

public class eventInfoDAO {

	public eventInfoDAO() {
		// TODO Auto-generated constructor stub
	}

	public void addEvent(EventInfo eventinf){
//		SessionFactory sf = cfg.buildSessionFactory();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(eventinf);
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}
	
	public void editeventinf(EventInfo eventinf){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(eventinf);
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}

	public ArrayList<EventInfo> getAlleventinf(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<EventInfo> eventinf =(ArrayList<EventInfo>) s.createQuery("from EventInfo ORDER BY eventId DESC").setFirstResult(0).setMaxResults(5).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	public ArrayList<EventInfo> getAllCompleteEventinf(int page, int pagesize){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<EventInfo> eventinf =(ArrayList<EventInfo>) s.createQuery("from EventInfo WHERE eventCompleted=1 ORDER BY eventId DESC").setFirstResult(page).setMaxResults(pagesize).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	public ArrayList<EventInfo> getAllWorkingEventinf(int page, int pagesize){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<EventInfo> eventinf =(ArrayList<EventInfo>) s.createQuery("from EventInfo WHERE eventCompleted=0 ORDER BY eventId DESC").setFirstResult(page).setMaxResults(pagesize).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
	
	public EventInfo geteventinfByName(String name){
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			ArrayList<EventInfo> eventinf =(ArrayList<EventInfo>) s.createQuery("From EventInfo where eventName = ?").setParameter(0, name).list();
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			return eventinf.get(0);
	}
	
	public static ArrayList<EventInfo> geteventinfByClientId(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<EventInfo> eventinf =(ArrayList<EventInfo>) s.createQuery("From EventInfo where clientId = ?").setParameter(0, id).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
}
	
	public static EventInfo geteventinfByID(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		EventInfo eventinf =(EventInfo) s.get(EventInfo.class, new Integer(id));
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return eventinf;
	}
}
