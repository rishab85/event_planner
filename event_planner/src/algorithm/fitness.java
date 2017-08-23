package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eventPD.GuestInfo;

public class fitness {

	public fitness() {
		
	}
	
	public ArrayList<Integer> calculatefitness(ArrayList<ArrayList<GuestInfo>> pop, int tablesize){
		
		
//		int ts = 6;
//		int checkcount = 0;
//		for(int i=0; i<pop.size(); i++){
//			int tbl = 1;
//			int gsi = pop.get(0).size();
//			for(int j = 0; j<pop.get(i).size(); j++){
//				checkcount++;
//				if(gsi>=ts){
//					gsi = gsi - ts;
//					for(int l=j; l<j+ts; l++){
//						pop.get(i).get(l).setTableAssigned(tbl);
//					}
//					j= j+(ts-1);
//					tbl++;
//				}else{
//					for(int l=j; l<j+gsi; l++){
//						pop.get(i).get(l).setTableAssigned(tbl);
//						gsi--;
//						j++;
//					}
//				}
//			}
//		}
		
//		System.out.println(checkcount);
		
//		for(int i=0; i<pop.size(); i++){
//			for(int j=0; j<pop.get(i).size(); j++){
//			System.out.println(pop.get(i).get(j).getGuestFname() + " " + pop.get(i).get(j).getTableAssigned());
//			}
//			System.out.println("---------");
//		}
		
		
		
		ArrayList<Integer> fitness = new ArrayList<Integer>();
		fitness.clear();
		int iFitness = 0;
		for(int i=0; i<pop.size(); i++){
			ArrayList<GuestInfo> guestinf = new ArrayList<GuestInfo>();
			guestinf = pop.get(i);
			int gs = guestinf.size();
			iFitness = 0;
			int start = 0;
			for(int j=0; j<guestinf.size(); j++){
				
				if(gs>=tablesize){
					gs = gs - tablesize;
					for(int l = j; l<j+tablesize; l++){
						List<String> seat = new ArrayList<String>();
						List<String> notseat = new ArrayList<String>();
						int temp = 0, total=0;
						seat = Arrays.asList(guestinf.get(l).getSeatWith().split(","));
						notseat = Arrays.asList(guestinf.get(l).getNotSeat().split(","));
						for(int m = start; m<start+tablesize; m++){
							
							if(seat.contains(String.valueOf(guestinf.get(m).getGuestId()))){
								//System.out.println("good" +" "+guestinf.get(l).getGuestFname() +" wants " +guestinf.get(m).getGuestFname());
								iFitness++;
								System.out.println("I am Here bro on one");
							}else if(notseat.contains(String.valueOf(guestinf.get(m).getGuestId()))){
//								System.out.println("Bad" +" "+guestinf.get(l).getGuestFname() +" doesnot wants " +guestinf.get(m).getGuestFname()+" "+ guestinf.get(l).getNotSeat());
								System.out.println("bad bro");
								total++;
							}else if(notseat.isEmpty()||notseat==null){
//								temp++;
								total++;
								System.out.println("empty bro");
							}else{
//								System.out.println("Fair " +guestinf.get(m).getGuestFname()) ;
								total++;
								System.out.println("fair bro");
							}
							
						}
					}
					j = j+(tablesize-1);
					start = start+tablesize;
					System.out.println("this is " + start);
				}else{
					for(int l=start; l<start+gs; l++){
						List<String> seat = new ArrayList<String>();
						List<String> notseat = new ArrayList<String>();
						int temp = 0, total=0;
						seat = Arrays.asList(guestinf.get(l).getSeatWith().split(","));
						notseat = Arrays.asList(guestinf.get(l).getNotSeat().split(","));
						for(int m = start; m<start+gs; m++){
							
							if(seat.contains(String.valueOf(guestinf.get(m).getGuestId()))){
//								System.out.println("good" +" "+guestinf.get(l).getGuestFname() +" wants " +guestinf.get(m).getGuestFname());
								iFitness++;
								System.out.println("I am Here bro two");
								total++;
							}else if(notseat.contains(String.valueOf(guestinf.get(m).getGuestId()))){
//								System.out.println("Bad" +" "+guestinf.get(l).getGuestFname() +" doesnot wants " +guestinf.get(m).getGuestFname()+" "+ guestinf.get(l).getNotSeat());
								iFitness--;
								
								total++;
							}else if(notseat.isEmpty()||notseat==null){
//								temp++;
								total++;
								System.out.println("I am Here bro three");
							}else{
//								System.out.println("Fair " +guestinf.get(m).getGuestFname()) ;
//								temp++;
								total++;
								System.out.println("I am Here bro four");
							}
							j++;
						}
					}
				}
				
			}
			System.out.println(iFitness + "---------");
			fitness.add(iFitness);
		}
		return fitness;
		
	}

}
