package web.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserPermit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Permit action;
	private User user;
	private boolean allowed;
	
	public UserPermit() {
		
	}
	
	@Id
	@Column(name = "action")
	@Enumerated(EnumType.STRING)
	public Permit getAction() {
		return action;
	}
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user")
	@JsonIgnore
	public User getUser() {
		return user;
	}

	@Column(name="allowed")
	public boolean isAllowed() {
		return allowed;
	}

	public void setAction(Permit action) {
		this.action = action;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}
	
	@Override
	public String toString() {
		return getUser().getUsername() + "@" + getAction().toString() + ": " + (isAllowed() ? "Allowed" : "Denied");
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;

	    UserPermit that = (UserPermit) obj;

	    if (action != null ? !action.equals(that.action) : that.action != null) return false;
	    return user != null ? user.equals(that.user) : that.user == null;
	}

	@Override
	public int hashCode() {
	    int result = action != null ? action.hashCode() : 0;
	    result = 31 * result + (user != null ? user.hashCode() : 0);
	    return result;
	}
	
	
}
