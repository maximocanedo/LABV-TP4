package web.exceptions;

public class NotFoundException extends CommonException {

	private static final long serialVersionUID = 1473684668098975313L;
	
	public NotFoundException() {
        this("Resource not found.");
    }
    public NotFoundException(String message) {
        this(message, "That's all we know. ");
    }
    public NotFoundException(String message, String description) {
        super(message, description, "err/not-found", 404);
    }

}
