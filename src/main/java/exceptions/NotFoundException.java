package exceptions;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1473684668098975313L;
	
	public NotFoundException() {
		super("The resource was not found.");
	}

}
