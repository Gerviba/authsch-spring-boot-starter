package hu.bme.sch.auth.authsch.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hu.bme.sch.auth.api")
public class AuthschProperties {

	private String tokenUrlBase;
	private String loginUrlBase;
	private String apiUrlBase;
	private String clientIdentifier;
	private String clientKey;

	public String getTokenUrlBase() {
		return tokenUrlBase;
	}

	public void setTokenUrlBase(String tokenUrlBase) {
		this.tokenUrlBase = tokenUrlBase;
	}

	public String getLoginUrlBase() {
		return loginUrlBase;
	}

	public void setLoginUrlBase(String loginUrlBase) {
		this.loginUrlBase = loginUrlBase;
	}

	public String getApiUrlBase() {
		return apiUrlBase;
	}

	public void setApiUrlBase(String apiUrlBase) {
		this.apiUrlBase = apiUrlBase;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

}
