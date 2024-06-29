package web.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;


@Entity
@Card(name="Usuario", size=24)
@Table(name="users")
public class User implements IUser {
	
	private String username;
	private String name;
	private String password;
	private boolean active = true;
	private Doctor doctor;
	private List<UserPermit> allowedPermits;
	
	public User() {}
	
	/* # Getters */
	
	@Id
	@Override
	@Column(name="username", unique=true, nullable=false)
	@Format(omitLabel=true, prefix="@")
	@JsonProperty("username")
	public String getUsername() {
		return this.username;
	}

	@Override
	@Column(name="name")
	@Format(label="Nombre")
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@Basic(fetch = FetchType.LAZY)
	@Column(name="password")
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@Override
	@Column(name="active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Usuario deshabilitado. ")
	@JsonProperty("active")
	public boolean isActive() {
		return this.active;
	}

	@OneToOne(mappedBy="user", optional = true)
	@JsonProperty("doctor")
	@JsonManagedReference
	public Doctor getDoctor() {
		return doctor;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "allowed")
	@JsonIgnore
    public List<UserPermit> getAllowedPermits() {
        return allowedPermits;
    }
	
	@Transient
	@JsonIgnore
	public boolean loadedPermissions() {
		return allowedPermits != null;
	}
	
	@Transient
    @JsonProperty("access")
	public Permit[] getPermits() {
		if(this.getAllowedPermits() == null) return new Permit[0];
		List<Permit> permits = new ArrayList<Permit>();
		permits.size();
		for(UserPermit p : getAllowedPermits()) {
			permits.add(p.getAction());
		}
		return (Permit[]) permits.toArray(new Permit[0]);	
	}
	
	@Transient
	@JsonIgnore
	public boolean can(Permit action) {
		for(Permit allowed : getPermits())
			if(allowed.equals(action)) 
				return true;
		return false;
	}
	
	
	/* # Setters */

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setActive(boolean status) {
		this.active = status;
	}
	
	public void setDoctor(Doctor medico) {
		this.doctor = medico;
	}
	
	public void setAllowedPermits(List<UserPermit> allowedPermits) {
        this.allowedPermits = allowedPermits;
    }
	
	/* # Otros métodos */
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}
	
}
