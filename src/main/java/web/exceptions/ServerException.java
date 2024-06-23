package web.exceptions;

public class ServerException extends CommonException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ServerException() {
        this("Internal error.");
    }
    public ServerException(String message) {
        this(message, "Server can't process your request in this moment. Try again later. ");
    }
    public ServerException(String message, String description) {
        super(message, description, "err/internal-error", 500);
    }
}
