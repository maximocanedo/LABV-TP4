package web.exceptions;

import web.entity.Permit;

public class NotAllowedException extends CommonException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotAllowedException() {
        this("Not allowed.");
    }
	public NotAllowedException(Permit[] permits) {
		this();
		String o = "";
		for(Permit p : permits) {
			o += "\n · " + p.toString();
		}
		this.setDescription("You lack the following permissions: " + o);
    }
	
	public NotAllowedException(Permit permit) {
		this();
		this.setDescription("You lack the " + permit.toString() + " permission. ");
    }
	
    public NotAllowedException(String message) {
        this(message, "You do not have the necessary permissions to access this resource. ");
    }
    public NotAllowedException(String message, String description) {
        super(message, description, "err/not-allowed", 403);
    }
}
