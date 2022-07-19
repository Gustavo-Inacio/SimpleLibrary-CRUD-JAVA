package util.Exceptions;

public class ActionClassMustImplementsActionInterface extends Exception{

	private static final long serialVersionUID = 1L;

	public ActionClassMustImplementsActionInterface(String msg) {
		super(msg);
	}
}
