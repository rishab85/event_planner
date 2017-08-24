package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import eventDAO.HibernateUtil;
import eventDAO.eventInfoDAO;
import eventPD.EventInfo;
import eventPD.GuestInfo;

public class algorithm {

	public algorithm(int id) {
		// TODO Auto-generated constructor stub
		
	
		eventInfoDAO eve = new eventInfoDAO();
		EventInfo eveinfo = eve.geteventinfByID(id);
		population po = new population();
		ArrayList<ArrayList<GuestInfo>> pop = new ArrayList<ArrayList<GuestInfo>>(po.generatePopulation(id));
		
		List<String> seat = new ArrayList<String>();
		seat = Arrays.asList(pop.get(0).get(0).getSeatWith().split(","));
		int deadend = 30000;
		
		int stoppoint = (int) ((seat.size()*pop.get(0).size())*0.8);
	
		
		int count=0;
		int maxfitness = 0;
		int samefitness = 0;
		int ind = 0;
		for(int i=0; ; i++){
			count++;
			
			fitness fit = new fitness();
			ArrayList<Integer> fitness = new ArrayList<Integer>(fit.calculatefitness(pop, eveinfo.getEventTablesize()));
			
			if(maxfitness == Collections.max(fitness)){
				samefitness++;
			}else{
				samefitness=0;
			}
			maxfitness = Collections.max(fitness);
			
			
			
			
			crossover cross = new crossover();
			ArrayList<ArrayList<GuestInfo>> newpop = new ArrayList<ArrayList<GuestInfo>>(cross.crossOverPop(pop, fitness, eveinfo.getEventTablesize()));
			pop=null;
			pop = newpop;
			
			System.out.println("maximum fitness" + Collections.max(fitness) + " popsize" + pop.size() + " " + samefitness);
			
			if(Collections.max(fitness)>=stoppoint || samefitness>deadend){
				System.out.println("solution found at generation " + count);
				System.out.println("Maximum fitness " + (Collections.max(fitness)/pop.get(0).size())*100 + "%" +" "+Collections.max(fitness));	
				break;
			}
		}
		
		System.out.println("--finaloutput");
		System.out.println("population size" + stoppoint);
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
