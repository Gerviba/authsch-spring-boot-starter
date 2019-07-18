package hu.bme.sch.auth.authsch.login;

import java.security.SecureRandom;

public class AuthschLoginConfig {

	private String loginOk = "/";
	private String loginFailed = "/login-failed";
	private String unexpectedError = "/error-login";
	private String loggedOut = "/logged-out";
	
	private String sessionAttributeName = "user";
	private String saltForUniqueId;
	
	{
		byte[] salt = new byte[64];
		new SecureRandom().nextBytes(salt);
		saltForUniqueId = new String(salt);
	}

	public String getLoginOk() {
		return loginOk;
	}

	public void setLoginOk(String loginOk) {
		this.loginOk = loginOk;
	}

	public String getLoginFailed() {
		return loginFailed;
	}

	public void setLoginFailed(String loginFailed) {
		this.loginFailed = loginFailed;
	}

	public String getUnexpectedError() {
		return unexpectedError;
	}

	public void setUnexpectedError(String unexpectedError) {
		this.unexpectedError = unexpectedError;
	}

	public String getLoggedOut() {
		return loggedOut;
	}

	public void setLoggedOut(String loggedOut) {
		this.loggedOut = loggedOut;
	}

	public String getSessionAttributeName() {
		return sessionAttributeName;
	}

	public void setSessionAttributeName(String sessionAttributeName) {
		this.sessionAttributeName = sessionAttributeName;
	}

	String getSaltForUniqueId() {
		return saltForUniqueId;
	}

	public void setSaltForUniqueId(String saltForUniqueId) {
		this.saltForUniqueId = saltForUniqueId;
	}


	
	
}
