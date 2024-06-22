package web.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;

@Entity
@Card(name = "Sesión", size = 36)
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
    @Format(label = "ID N.º")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Format(label = "Estado", whenTrue = "Activo", whenFalse = "Inactivo")
    @Column(name="active")
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Format(label = "Expira")
    @Column(name="expirationDate")
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Format(label = "ID del dispositivo")
    @Column(name="deviceIdentifier")
    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }
    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    @Format(label = "Agente")
    @Column(name="deviceAgent")
    public String getDeviceAgent() {
        return deviceAgent;
    }
    public void setDeviceAgent(String deviceAgent) {
        this.deviceAgent = deviceAgent;
    }

    @Format(omitLabel = true, prefix = "Versión ")
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
    public String toString() {
    	return Formatter.of(this).toString();
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
