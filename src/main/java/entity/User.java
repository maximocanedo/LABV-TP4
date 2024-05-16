package entity;


import javax.persistence.*;

import utils.FormattedLine;
import utils.IFormattedLine;
import utils.FormattedLine.Alignment;

@Entity
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
	
	public User() {
		
	}
	
	

	@OneToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
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
	
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public String toString() {		
		IFormattedLine header = new FormattedLine("[User]");
		header.setLineSize(48);
		header.setTopHeader(true);
		header.setAlignment(Alignment.RIGHT);
		String cont = "";
		String[] lines = new String[] {
			name,
			"(@" + username + ")"
		};
		for(String line: lines) {
			cont += line + "\n";
		}
		IFormattedLine content = new FormattedLine(cont);
		content.setLineSize(48);
		IFormattedLine contact = new FormattedLine(
			this.isActive() ? "" : "Usuario deshabilitado. "
		);
		contact.setAlignment(Alignment.RIGHT);
		contact.setLineSize(48);
		
		IFormattedLine end = new FormattedLine("···");
		end.setAlignment(FormattedLine.Alignment.CENTER);
		end.setTopHeader(true);
		end.setHeaderMiddleDelimiters('—');
		end.setLineSize(48);
		
		String tot = header.toString() + content.toString() + contact.toString();
		
		return tot + end.toString();
	}
	
}
