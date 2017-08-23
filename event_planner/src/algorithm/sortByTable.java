package algorithm;

import java.util.Comparator;

import eventPD.GuestInfo;

public class sortByTable implements Comparator<GuestInfo> {

	public sortByTable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(GuestInfo o1, GuestInfo o2) {
		// TODO Auto-generated method stub
		Integer table1 = o1.getTableAssigned();
		Integer table2 = o2.getTableAssigned();
		return table1.compareTo(table2);
	}

}
