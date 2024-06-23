package web.entity.input;

public class UserCredentials {
	private String username;
	private String password;
	private String newPassword;
	public UserCredentials() {}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String password) {
		this.newPassword = password;
	}
	
}
