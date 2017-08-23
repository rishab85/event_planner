package algorithm;

import java.util.ArrayList;
import java.util.Collections;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import eventDAO.HibernateUtil;
import eventPD.GuestInfo;

public class algorithm {

	public algorithm() {
		// TODO Auto-generated constructor stub
		
	
		population po = new population();
		ArrayList<ArrayList<GuestInfo>> pop = new ArrayList<ArrayList<GuestInfo>>(po.generatePopulation(42));
		int count=0;
		for(int i=0; ; i++){
			count++;
			fitness fit = new fitness();
			ArrayList<Integer> fitness = new ArrayList<Integer>(fit.calculatefitness(pop, 6));
			
			
			
			
			
			crossover cross = new crossover();
			ArrayList<ArrayList<GuestInfo>> newpop = new ArrayList<ArrayList<GuestInfo>>(cross.crossOverPop(pop, fitness));
			pop=null;
			pop = newpop;
			
			System.out.println("maximum fitness" + Collections.max(fitness));
			
			if(Collections.max(fitness)>=33){
				System.out.println("solution found at generation " + count);
				System.out.println("Maximum fitness " + (Collections.max(fitness)/pop.get(0).size())*100 + "%" +" "+Collections.max(fitness));	

				break;
			}
		}
		
		System.out.println("--finaloutput");
			for(int j=0; j<pop.get(0).size(); j++){
			System.out.println(pop.get(0).get(j).getId() + " " + pop.get(0).get(j).getTableAssigned());
			}
			System.out.println("---------");
		
		
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			for(int i=0; i<pop.get(0).size(); i++){
				GuestInfo guest = (GuestInfo) pop.get(0).get(i);
				s.update(guest);
			}
			s.flush();
			s.clear();
			tx.commit();
			s.close();
		
	}

	
	
}
