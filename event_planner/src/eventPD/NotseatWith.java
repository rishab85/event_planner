// default package
// Generated Aug 9, 2017 11:02:06 PM by Hibernate Tools 4.3.1.Final
package eventPD;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * NotseatWith generated by hbm2java
 */
@XmlRootElement
@Entity
@Table(name = "notseat_with", catalog = "event_planner")
public class NotseatWith implements java.io.Serializable {

	private Integer id;
	private int guestJd;
	private int notseatId;
	private int eventId;
	private GuestInfo guest;
	
	public NotseatWith() {
	}

	public NotseatWith(int guestJd, int notseatId, int eventId) {
		this.guestJd = guestJd;
		this.notseatId = notseatId;
		this.eventId = eventId;
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

	@Column(name = "guest_id", nullable = false)
	public int getGuestJd() {
		return this.guestJd;
	}

	public void setGuestJd(int guestJd) {
		this.guestJd = guestJd;
	}

	@Column(name = "notseat_id", nullable = false)
	public int getNotseatId() {
		return this.notseatId;
	}

	@XmlElement
	public void setNotseatId(int notseatId) {
		this.notseatId = notseatId;
	}

	@Column(name = "event_id", nullable = false)
	public int getEventId() {
		return this.eventId;
	}

	@XmlElement
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	@ManyToOne
	@JoinColumn(name="guest_id", referencedColumnName = "guest_id", insertable=false, updatable=false, nullable=false)
	public GuestInfo getGuest() {
		return guest;
	}

	@XmlElement
	public void setGuest(GuestInfo guest) {
		this.guest = guest;
	}

}
