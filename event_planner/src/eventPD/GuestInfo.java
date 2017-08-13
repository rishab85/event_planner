// default package
// Generated Aug 11, 2017 7:19:09 PM by Hibernate Tools 4.3.1.Final
package eventPD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GuestInfo generated by hbm2java
 */
@Entity
@Table(name = "guest_info", catalog = "event_planner")
public class GuestInfo implements java.io.Serializable {

	private Integer id;
	private String guestFname;
	private int tableAssigned;
	private int eventId;
	private int guestId;
	private String guestLname;
	private String seatWith;
	private String notSeat;

	public GuestInfo() {
	}

	public GuestInfo(String guestFname, int tableAssigned, int eventId, int guestId, String guestLname) {
		this.guestFname = guestFname;
		this.tableAssigned = tableAssigned;
		this.eventId = eventId;
		this.guestId = guestId;
		this.guestLname = guestLname;
	}

	public GuestInfo(String guestFname, int tableAssigned, int eventId, int guestId, String guestLname, String seatWith,
			String notSeat) {
		this.guestFname = guestFname;
		this.tableAssigned = tableAssigned;
		this.eventId = eventId;
		this.guestId = guestId;
		this.guestLname = guestLname;
		this.seatWith = seatWith;
		this.notSeat = notSeat;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "guest_fname", nullable = false, length = 50)
	public String getGuestFname() {
		return this.guestFname;
	}

	public void setGuestFname(String guestFname) {
		this.guestFname = guestFname;
	}

	@Column(name = "table_assigned", nullable = false)
	public int getTableAssigned() {
		return this.tableAssigned;
	}

	public void setTableAssigned(int tableAssigned) {
		this.tableAssigned = tableAssigned;
	}

	@Column(name = "event_id", nullable = false)
	public int getEventId() {
		return this.eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	@Column(name = "guest_id", nullable = false)
	public int getGuestId() {
		return this.guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	@Column(name = "guest_lname", nullable = false, length = 50)
	public String getGuestLname() {
		return this.guestLname;
	}

	public void setGuestLname(String guestLname) {
		this.guestLname = guestLname;
	}

	@Column(name = "seat_with", length = 100)
	public String getSeatWith() {
		return this.seatWith;
	}

	public void setSeatWith(String seatWith) {
		this.seatWith = seatWith;
	}

	@Column(name = "not_seat", length = 100)
	public String getNotSeat() {
		return this.notSeat;
	}

	public void setNotSeat(String notSeat) {
		this.notSeat = notSeat;
	}

}
