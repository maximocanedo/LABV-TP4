package web.exceptions;

public class ValidationException extends CommonException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ValidationException() {
        this("Validation error.");
    }
    public ValidationException(String message) {
        this(message, "That's all we know. ");
    }
    public ValidationException(String message, String description) {
        super(message, description, "err/validation-error", 400);
    }
}
