package entity;


import javax.persistence.*;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;


@Entity
@Card(name="Usuario", size=24)
@Table(name="users")
public class User {
	
	@Id
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name="password")
	private String password;
	
	@Column(name="active")
	private boolean active = true;
	
	@OneToOne(mappedBy="usuario")
	private Medico medico;
	
	public User() {
		
	}
	
	


	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
	@Format(label="Nombre")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Format(omitLabel=true, prefix="@")
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setActiveState(boolean status) {
		this.active = status;
	}
	
	@Format(label="Estado")
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}
	
}
