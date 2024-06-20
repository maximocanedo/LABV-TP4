package exceptions;

public class InvalidCredentialsException extends CommonException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidCredentialsException() {
        this("Invalid credentials.");
    }
    public InvalidCredentialsException(String message) {
        this(message, "Use a different password, username, or try again later. ");
    }
    public InvalidCredentialsException(String message, String description) {
        super(message, description, "err/invalid-credentials", 401);
    }
}
