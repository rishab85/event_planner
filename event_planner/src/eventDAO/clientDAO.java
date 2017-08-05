package eventDAO;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import eventPD.ClientInfo;

public class clientDAO {

	public static void addClient(ClientInfo client){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(client);
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}
	
	public static void editClient(ClientInfo client){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(client);
		s.flush();
		s.clear();
		tx.commit();
		s.close();
	}

	public static ArrayList<ClientInfo> getAllClient(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<ClientInfo> client =(ArrayList<ClientInfo>) s.createQuery("from ClientInfo").list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return client;
	}
	
	public static ClientInfo getClientByName(String name){
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			ArrayList<ClientInfo> client =(ArrayList<ClientInfo>) s.createQuery("From ClientInfo where clientName = ?").setParameter(0, name).list();
			s.flush();
			s.clear();
			tx.commit();
			s.close();
			return client.get(0);
	}
	
	public static ClientInfo getClientByID(int id){
//		SessionFactory sf = cfg.buildSessionFactory();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ClientInfo client =(ClientInfo) s.get(ClientInfo.class, new Integer(id));
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		return client;
	}
	
	
	
	public clientDAO() {
		// TODO Auto-generated constructor stub
		
	}

}
