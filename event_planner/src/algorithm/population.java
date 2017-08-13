package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import eventDAO.HibernateUtil;
import eventDAO.eventInfoDAO;
import eventDAO.guestDAO;
import eventPD.EventInfo;
import eventPD.GuestInfo;

public class population {

	public population() {
		
	}
	
	public ArrayList<ArrayList<GuestInfo>> generatePopulation(int id){
		ArrayList<GuestInfo> guestinf = new ArrayList<GuestInfo>();
		ArrayList<ArrayList<GuestInfo>> pop = new ArrayList<ArrayList<GuestInfo>>();
		Random rand = new Random();
		guestDAO gu = new guestDAO();
		EventInfo ef = new EventInfo();
		ef = eventInfoDAO.geteventinfByID(42);
		int tableSize = ef.getEventTablesize();
		int gs = 0;
		int in = 0;
		
		guestinf = gu.getAllGuest(42);
		String[] seat = guestinf.get(0).getSeatWith().split(",");
		int groupSize = seat.length+1;
		int totalTable = (guestinf.size()/ef.getEventTablesize())+1;
		
		for(int i=0; i<1; i++){
			gs = guestinf.size();
			Integer[] ran = randomNumbers(totalTable);
			int count = 0;
			for(int j=0; j<guestinf.size(); j++){
				if(gs>=groupSize){
					gs = gs-groupSize;
					System.out.println(gs);
					for(int l=j; l<j+groupSize; l++){
						guestinf.get(l).setTableAssigned(ran[count]);
						System.out.println(guestinf.get(l).getGuestLname() + " " + guestinf.get(l).getTableAssigned());
					}
					j=j+(groupSize-1);
					count++;
					if(count>=ran.length){
						count=0;
						ran = randomNumbers(totalTable);
					}
					
				}else{
					for(int l=j; l<j+gs; l++){
						guestinf.get(l).setTableAssigned(ran[count]);
						System.out.println(guestinf.get(l).getGuestLname() + " " + guestinf.get(l).getTableAssigned());
						j++;
						gs--;
					}
					count++;
				}
				
				
			}
			pop.add(guestinf);
			guestinf = pop.get(i);
			System.out.println("---------------------------");
		}
		
		for(int i=0; i<1; i++){
			for(int j=0; j<pop.get(0).size(); j++){
				SessionFactory sf = HibernateUtil.getSessionFactory();
				Session s = sf.openSession();
				Transaction tx = s.beginTransaction();
				s.update(pop.get(0).get(j));
				s.flush();
				s.clear();
				tx.commit();
				s.close();
			}
		}
		return pop;
	}
	
	private Integer[] randomNumbers(int numberRange) {
	    //Create and shuffle array
	    Integer[] randomNumbers = new Integer[numberRange];
	    for (int i = 0; i < randomNumbers.length; i++) {
	        randomNumbers[i] = 1 + i;
	    }
	    Collections.shuffle(Arrays.asList(randomNumbers));
	    return randomNumbers;
	}
	
}
