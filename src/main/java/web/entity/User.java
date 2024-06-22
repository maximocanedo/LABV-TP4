package web.entity;


import javax.persistence.*;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;


@Entity
@Card(name="Usuario", size=24)
@Table(name="users")
public class User {
	
	private String username;
	private String name;
	private String password;
	private boolean active = true;
	private Doctor doctor;
	
	public User() {}
	
	/* # Getters */
	
	@Id
	@Column(name="username", unique=true, nullable=false)
	@Format(omitLabel=true, prefix="@")
	public String getUsername() {
		return this.username;
	}

	@Column(name="name")
	@Format(label="Nombre")
	public String getName() {
		return name;
	}

	@Basic(fetch = FetchType.LAZY)
	@Column(name="password")
	public String getPassword() {
		return this.password;
	}

	@Column(name="active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Usuario deshabilitado. ")
	public boolean isActive() {
		return this.active;
	}

	@OneToOne(mappedBy="user", optional = true)
	public Doctor getDoctor() {
		return doctor;
	}

	/* # Setters */
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActive(boolean status) {
		this.active = status;
	}
	
	public void setDoctor(Doctor medico) {
		this.doctor = medico;
	}
	
	/* # Otros métodos */
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}
	
}
