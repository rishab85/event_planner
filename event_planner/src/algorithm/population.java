package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
	private ArrayList<ArrayList<GuestInfo>> population;
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
		int totalTable = (guestinf.size()/ef.getEventTablesize())+1;
		
//		for(int i=0; i<1; i++){
//			gs = guestinf.size();
//			Integer[] ran = randomNumbers(totalTable);
//			int count = 0;
//			for(int j=0; j<guestinf.size(); j++){
//				if(gs>=groupSize){
//					gs = gs-groupSize;
//					System.out.println(gs);
//					for(int l=j; l<j+groupSize; l++){
//						guestinf.get(l).setTableAssigned(ran[count]);
//						System.out.println(guestinf.get(l).getGuestLname() + " " + guestinf.get(l).getTableAssigned());
//					}
//					j=j+(groupSize-1);
//					count++;
//					if(count>=ran.length){
//						count=0;
//						ran = randomNumbers(totalTable);
//					}
//					
//				}else{
//					for(int l=j; l<j+gs; l++){
//						guestinf.get(l).setTableAssigned(ran[count]);
//						System.out.println(guestinf.get(l).getGuestLname() + " " + guestinf.get(l).getTableAssigned());
//						j++;
//						gs--;
//					}
//					count++;
//				}
//				
//				
//			}
//			pop.add(guestinf);
//			guestinf = pop.get(i);
//			System.out.println("---------------------------");
//		}
		
		for(int i=0; i<100; i++){
			gs = guestinf.size();
			int count = 1;
			ArrayList<GuestInfo> gi = new ArrayList<GuestInfo>();
			gi = gu.getAllGuest(42);
			Collections.shuffle(gi);
			for(int j=0; j<gi.size(); j++){
				
				
				if(gs>=tableSize){
					gs = gs - tableSize;
					for(int l=j; l<j+tableSize; l++){
						gi.get(l).setTableAssigned(count);
//						System.out.println(guestinf.get(l).getGuestFname() + " " + guestinf.get(l).getTableAssigned() + " " + guestinf.get(l).getNotSeat() );
					}
					j = j + (tableSize-1);
				}else{
					for(int l=j; l<j+gs; l++){
						gi.get(l).setTableAssigned(count);
//						System.out.println(guestinf.get(l).getGuestFname() + " " + guestinf.get(l).getTableAssigned());
						gs--;
						j++;
					}
				}
				count++;
			}
//			System.out.println("---------------");
			pop.add(gi);
			
		}
		
		
//		for(int i=0; i<pop.size(); i++){
//			System.out.println(pop.get(i).get(0).getGuestFname());
//		}
//		fitness fit = new fitness();
//		ArrayList<Integer> fitness = fit.calculatefitness(pop, tableSize);
//
//		
//		crossover cross = new crossover();
//		fit.calculatefitness(cross.crossOverPop(pop, fitness), tableSize);
		
//		for(int i=0; i<1; i++){
//			for(int j=0; j<pop.get(0).size(); j++){
//				SessionFactory sf = HibernateUtil.getSessionFactory();
//				Session s = sf.openSession();
//				Transaction tx = s.beginTransaction();
//				s.update(pop.get(0).get(j));
//				s.flush();
//				s.clear();
//				tx.commit();
//				s.close();
//			}
//		}
//		
//		for(int l=0; l<pop.size(); l++){
//			for(int j=0; j<pop.get(0).size(); j++){
//				System.out.println(pop.get(l).get(j).getGuestFname() + " " + pop.get(l).get(j).getTableAssigned() );
//			}
//			System.out.println("-------");
//		}
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
