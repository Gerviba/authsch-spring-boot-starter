package hu.bme.sch.auth.authsch.exception;

@SuppressWarnings("serial")
public class AuthschInvalidStateException extends Exception {

	public AuthschInvalidStateException() {
		super();
	}

	public AuthschInvalidStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthschInvalidStateException(String message) {
		super(message);
	}

	public AuthschInvalidStateException(Throwable cause) {
		super(cause);
	}

}
