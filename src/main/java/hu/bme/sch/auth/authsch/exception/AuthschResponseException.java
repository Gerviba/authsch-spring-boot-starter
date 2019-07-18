package hu.bme.sch.auth.authsch.exception;

@SuppressWarnings("serial")
public class AuthschResponseException extends RuntimeException {

    public AuthschResponseException(String message) {
        super(message);
    }

    public AuthschResponseException(String message, Throwable t) {
        super(message, t);
    }
    
}
