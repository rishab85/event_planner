package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import eventPD.GuestInfo;

public class sortByPerson implements Comparator<GuestInfo> {

	public sortByPerson() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<GuestInfo> sort(ArrayList<GuestInfo> guestinf){
		ArrayList<GuestInfo> ginf = new ArrayList<GuestInfo>(guestinf);
		Collections.sort(ginf, new Comparator<GuestInfo>() {
			@Override
			public int compare(GuestInfo o1, GuestInfo o2) {
				// TODO Auto-generated method stub
				Integer table1 = o1.getTableAssigned();
				Integer table2 = o2.getTableAssigned();
				return table1.compareTo(table2);
			}
	    });
		return ginf;
		
	}
	
	@Override
	public int compare(GuestInfo o1, GuestInfo o2) {
		// TODO Auto-generated method stub
		
		return o1.getGuestFname().compareTo(o2.getGuestFname());
	}
}
