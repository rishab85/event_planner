package systemDAO;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import eventDAO.HibernateUtil;
import systemPD.Token;
import systemPD.UserProfile;

public class UserProfileDAO {
	
	public void addUser(UserProfile user){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(user);
		s.flush();
		tx.commit();
		s.close();
	}
	
	public void saveUser(UserProfile user){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(user);
		s.flush();
		tx.commit();
		s.close();
	
	}
	
	public static UserProfile getUserByID(int id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		UserProfile user =(UserProfile) s.get(UserProfile.class, new Integer(id));
		s.flush();
		tx.commit();
		s.close();
	
		
		return user;
	}

	public static UserProfile getUserByName(String username){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<UserProfile> user =(ArrayList<UserProfile>) s.createQuery("From UserProfile where userName = ?").setParameter(0, username).list();
		s.flush();
		tx.commit();
		s.close();
		return user.get(0);
	}
	
	
	public static ArrayList<UserProfile> getAllUser(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<UserProfile> users =  (ArrayList<UserProfile>) s.createQuery("from UserProfile")
				.list();
		s.flush();
		tx.commit();
		s.close();
		return users;
	}
	
	public static ArrayList<Token> getAllToken(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<Token> tknn =  (ArrayList<Token>) s.createQuery("from Token")
				.list();
		s.flush();
		tx.commit();
		s.close();
		return tknn;
	}
	
	public UserProfileDAO() {
		
	}

}
