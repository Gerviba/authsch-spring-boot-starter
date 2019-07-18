package hu.bme.sch.auth.authsch.login;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hu.bme.sch.auth.login")
public class AuthschLoginProperties {

	private String loginOk;
	private String loginFailed;
	private String unexpectedError;
	private String loggedOut;

	private String sessionAttributeName;
	private String saltForUniqueId;

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
