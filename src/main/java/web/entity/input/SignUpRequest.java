package web.entity.input;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;

import web.entity.Doctor;
import web.formatter.Formatter;


public class SignUpRequest {
	
	private String username;
	private String name;
	private String password;
	@Nullable
	private Doctor doctor;
	
	public SignUpRequest() {}
	
	@JsonProperty("username")
	public String getUsername() {
		return this.username;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("password")
	public String getPassword() {
		return this.password;
	}
	
	@Nullable
	@JsonProperty("doctor")
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
	
	public void setDoctor(Doctor medico) {
		this.doctor = medico;
	}
	
	/* # Otros métodos */
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}
	
}
