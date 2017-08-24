package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import eventPD.GuestInfo;

public class crossover {

	public crossover() {
		// TODO Auto-generated constructor stub
	}

	
	public ArrayList<ArrayList<GuestInfo>> crossOverPop(ArrayList<ArrayList<GuestInfo>> pop, ArrayList<Integer> fitness, int ts){
		
		for(int i=0; i<3; i++){
			int min = fitness.indexOf(Collections.min(fitness));
			pop.remove(min);
			fitness.remove(min);
		}
		
		
//		ArrayList<GuestInfo> replacepop = new ArrayList<GuestInfo>();
//		for (GuestInfo point : pop.get(fitness.indexOf(Collections.max(fitness)))) {
//		    replacepop.add(point);
//		}
//		
//		pop.add(replacepop);
		
		
		ArrayList<Integer> picker = new ArrayList<Integer>();
		
		int count = 0;
		int max = 0;
		int maxindex = 0;
		int second = 0, third=0;
		int secondindex = 0,thirdindex=0;
		for(int i=0; i<fitness.size(); i++){
			int check = 0;
			for(int j=0; j<fitness.get(i); j++){
				picker.add(i);
				count++;
				check++;
			}
			if(check>max){
				second = max;
				secondindex = maxindex;
				max=check;
				maxindex = i;
			}else if(check>second){
				third = second;
				thirdindex = secondindex;	
				second=check;
				secondindex = i;
			}else if(check>third){
				third = check;
				thirdindex = i;
			}
		}
	
		maxfitness maxfit = new maxfitness();
		maxfit.setGuest(pop.get(maxindex));
		
		ArrayList<GuestInfo> test = new ArrayList<GuestInfo>();
		test = maxfit.getGuest();
//		System.out.println("---newgen");
//		for(int i=0; i<pop.size(); i++){
//			for(int j=0; j<pop.get(i).size(); j++){
//			System.out.println(pop.get(i).get(j).getGuestFname() + " " + pop.get(i).get(j).getTableAssigned());
//			}
//			System.out.println("---------");
//		}
		
		System.out.println("Maxx -- " + Collections.max(fitness));
		
//		for(int j=0; j<pop.get(maxindex).size(); j++){
//			System.out.println(pop.get(maxindex).get(j).getGuestFname());
//		}
		
		Integer[] ran = randomNumbers(picker.size()-1);
		int half = pop.get(0).size()/2;
		ArrayList<ArrayList<GuestInfo>> newPop = new ArrayList<ArrayList<GuestInfo>>();

		
		ArrayList<GuestInfo> testpop = new ArrayList<GuestInfo>();
		for (GuestInfo point : pop.get(maxindex)) {
		    testpop.add(point);
		}
		
		ArrayList<GuestInfo> testpop2 = new ArrayList<GuestInfo>();
		for (GuestInfo point : pop.get(secondindex)) {
		    testpop2.add(point);
		}
		
		ArrayList<GuestInfo> testpop3 = new ArrayList<GuestInfo>();
		for (GuestInfo point : pop.get(thirdindex)) {
		    testpop3.add(point);
		}
		
		newPop.add(testpop);
		newPop.add(testpop2);
		newPop.add(testpop3);
//		newPop.add(pop.get(picker.get(secondindex)));
		
		for(int i=0; i<pop.size(); i++){
			Collections.sort(pop.get(i), new sortByPerson());
		}
		int index = 0;
		for(int i=0; i<pop.size(); i++){
			ArrayList<GuestInfo> child = new ArrayList<GuestInfo>();
			ArrayList<GuestInfo> parentA = new ArrayList<GuestInfo>(pop.get(picker.get(ran[index])));
			int old = index;
			index = index+1;
			while(picker.get(ran[index])==picker.get(ran[old])){
				index = index+1;
			}
			ArrayList<GuestInfo> parentB = new ArrayList<GuestInfo>(pop.get(picker.get(ran[index])));
//			System.out.println(pop.get(picker.get(ran[i])).size());
//			parentA = pop.get(picker.get(ran[index]));
//			index++;
//			parentB = pop.get(picker.get(ran[index]));	
			for(int j=0; j<pop.get(i).size(); j++){
				if(j<=half){
//					child.get(j).setEventId(parentA.get(j).getEventId());
//					child.get(j).setGuestFname(parentA.get(j).getGuestFname());
//					child.get(j).setGuestId(parentA.get(j).getGuestId());
//					child.get(j).setGuestLname(parentA.get(j).getGuestLname());
//					child.get(j).setNotSeat(parentA.get(j).getNotSeat());
//					child.get(j).setSeatWith(parentA.get(j).getSeatWith());
//					child.get(j).setTableAssigned(parentA.get(j).getTableAssigned());
//					child.get(j).setId(parentA.get(j).getId());
					GuestInfo guest = new GuestInfo();
					guest.setEventId(parentA.get(j).getEventId());
					guest.setGuestFname(parentA.get(j).getGuestFname());
					guest.setGuestId(parentA.get(j).getGuestId());
					guest.setGuestLname(parentA.get(j).getGuestLname());
					guest.setId(parentA.get(j).getId());
					guest.setNotSeat(parentA.get(j).getNotSeat());
					guest.setSeatWith(parentA.get(j).getSeatWith());
					guest.setTableAssigned(parentA.get(j).getTableAssigned());
					child.add(guest);
					//System.out.println(parentA.get(j).getTableAssigned() + " " + parentA.get(j).getGuestFname() + " | "+ parentB.get(j).getTableAssigned() + " " + parentB.get(j).getGuestFname());
				}else{
//					child.get(j).setEventId(parentB.get(j).getEventId());
//					child.get(j).setGuestFname(parentB.get(j).getGuestFname());
//					child.get(j).setGuestId(parentB.get(j).getGuestId());
//					child.get(j).setGuestLname(parentB.get(j).getGuestLname());
//					child.get(j).setNotSeat(parentB.get(j).getNotSeat());
//					child.get(j).setSeatWith(parentB.get(j).getSeatWith());
//					child.get(j).setTableAssigned(parentB.get(j).getTableAssigned());
//					child.get(j).setId(parentB.get(j).getId());
					GuestInfo guest = new GuestInfo();
					guest.setEventId(parentB.get(j).getEventId());
					guest.setGuestFname(parentB.get(j).getGuestFname());
					guest.setGuestId(parentB.get(j).getGuestId());
					guest.setGuestLname(parentB.get(j).getGuestLname());
					guest.setId(parentB.get(j).getId());
					guest.setNotSeat(parentB.get(j).getNotSeat());
					guest.setSeatWith(parentB.get(j).getSeatWith());
					guest.setTableAssigned(parentB.get(j).getTableAssigned());

					child.add(guest);
					//System.out.println(parentA.get(j).getTableAssigned() + " " + parentA.get(j).getGuestFname() + " | "+ parentB.get(j).getTableAssigned() + " " + parentB.get(j).getGuestFname());
				}
				
			}
			Random rann = new Random();
			int ranindex=0;
//			System.out.println("---------");
			for(int m = 0; m<rann.nextInt(3); m++){
			Random rand = new Random();
			Integer[] random = randomNumbers(child.size()-1);
			int temp = child.get(random[ranindex]).getTableAssigned();
			child.get(random[ranindex]).setTableAssigned(child.get(random[ranindex+1]).getTableAssigned());
			child.get(random[ranindex+1]).setTableAssigned(temp);
			ranindex++;
			}
//			Collections.swap(child, 0, random);
			newPop.add(child);
//			for(int l = 0; l<child.size(); l++){
//				
//				System.out.println(child.get(l).getGuestFname()+" "+child.get(l).getTableAssigned());
//			}
		}
		
		//mutatation
		
		
		
		for(int i=0; i<newPop.size(); i++){
			Collections.sort(newPop.get(i), new sortByTable());
		}
		
		int checkcount = 0;
		for(int i=0; i<newPop.size(); i++){
			int tbl = 1;
			int gsi = newPop.get(0).size();
			for(int j = 0; j<newPop.get(i).size(); j++){
				checkcount++;
				if(gsi>=ts){
					gsi = gsi - ts;
					for(int l=j; l<j+ts; l++){
						newPop.get(i).get(l).setTableAssigned(tbl);
					}
					j= j+(ts-1);
					tbl++;
				}else{
					for(int l=j; l<j+gsi; l++){
						newPop.get(i).get(l).setTableAssigned(tbl);
						gsi--;
						j++;
					}
				}
			}
		}
		
//		System.out.println(checkcount);
//		System.out.println("--start new gen");
//		for(int i=0; i<newPop.size(); i++){
//			for(int j=0; j<newPop.get(i).size(); j++){
//			System.out.println(newPop.get(i).get(j).getGuestFname() + " " + newPop.get(i).get(j).getTableAssigned());
//			System.out.println("--test---");
//			System.out.println(testpop.get(j).getGuestFname());
//			System.out.println("--test---");
//			}
//			System.out.println("---------");
//			System.out.println("---------");
//			System.out.println("---------");
//		}
		
		return newPop;
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
