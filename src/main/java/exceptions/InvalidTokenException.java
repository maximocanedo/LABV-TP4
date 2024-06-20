package exceptions;

public class InvalidTokenException extends CommonException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidTokenException() {
        this("Invalid token");
    }
    public InvalidTokenException(String message) {
        this(message, "That's all we know. ");
    }
    public InvalidTokenException(String message, String description) {
        super(message, description, "err/invalid-token", 498);
    }
}
