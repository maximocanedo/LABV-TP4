package web.entity.view;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.entity.IUser;
import web.entity.Permit;
import web.entity.UserPermit;
import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;


@Entity
@Immutable
@Card(name="Usuario", size=24)
@Table(name="users")
public class UserView implements IUser {
	
	public static UserView from(IUser data) {
		if(data == null) return null;
		UserView view = new UserView();
		view.setUsername(data.getUsername());
		view.setName(data.getName());
		view.setActive(data.isActive());
		return view;
	}
	
	private String username;
	private String name;
	private boolean active = true;
	private DoctorMinimalView doctor;
	private List<UserPermit> allowedPermits;
	
	public UserView() {}
	
	/* # Getters */
	
	@Id
	@Override
	@JsonProperty("username")
	@Format(omitLabel=true, prefix="@")
	@Column(name="username", unique=true, nullable=false)
	public String getUsername() {
		return this.username;
	}

	@Override
	@Column(name="name")
	@JsonProperty("name")
	@Format(label="Nombre")
	public String getName() {
		return name;
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
	public DoctorMinimalView getDoctor() {
		return doctor;
	}
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

	@Override
	public void setActive(boolean status) {
		this.active = status;
	}
	
	public void setDoctor(DoctorMinimalView medico) {
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
