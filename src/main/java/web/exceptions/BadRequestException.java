package web.exceptions;

public class BadRequestException extends CommonException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BadRequestException() {
        this("Bad request.");
    }
    public BadRequestException(String message) {
        this(message, "Check request data, and try again. ");
    }
    public BadRequestException(String message, String description) {
        super(message, description, "err/bad-request", 400);
    }
}
