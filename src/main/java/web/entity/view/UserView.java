package web.entity.view;


import javax.persistence.*;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;


@Entity
@Immutable
@Card(name="Usuario", size=24)
@Table(name="users")
public class UserView {
	
	private String username;
	private String name;
	private boolean active = true;
	private DoctorMinimalView doctor;
	
	public UserView() {}
	
	/* # Getters */
	
	@Id
	@Column(name="username", unique=true, nullable=false)
	@Format(omitLabel=true, prefix="@")
	@JsonProperty("username")
	public String getUsername() {
		return this.username;
	}

	@Column(name="name")
	@Format(label="Nombre")
	@JsonProperty("name")
	public String getName() {
		return name;
	}

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

	/* # Setters */
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActive(boolean status) {
		this.active = status;
	}
	
	public void setDoctor(DoctorMinimalView medico) {
		this.doctor = medico;
	}
	
	/* # Otros métodos */
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}
	
}
