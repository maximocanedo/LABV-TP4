package entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

	private int id;
    private User user;
    private boolean active = true;
    private Date expirationDate;
    private String deviceIdentifier;
    private String deviceAgent;
    private int version;
    private Date creationDate;

    public Ticket() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sessionId")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Column(name="active")
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name="expirationDate")
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name="deviceIdentifier")
    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }
    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    @Column(name="deviceAgent")
    public String getDeviceAgent() {
        return deviceAgent;
    }
    public void setDeviceAgent(String deviceAgent) {
        this.deviceAgent = deviceAgent;
    }

    @Column(name="version")
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name="creationDate")
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket s = (Ticket) o;
        return Objects.equals(this.getId(), s.getId());
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
	
}
