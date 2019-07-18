package hu.bme.sch.auth.authsch.exception;

@SuppressWarnings("serial")
public class AuthschAuthenticationFailedException extends Exception {

	public AuthschAuthenticationFailedException() {
		super();
	}

	public AuthschAuthenticationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthschAuthenticationFailedException(String message) {
		super(message);
	}

	public AuthschAuthenticationFailedException(Throwable cause) {
		super(cause);
	}

}
