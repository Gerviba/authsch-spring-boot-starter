package hu.bme.sch.auth.authsch;

public class AuthschConfig {

    private String tokenUrlBase = "https://auth.sch.bme.hu/oauth2/token";
    private String loginUrlBase = "https://auth.sch.bme.hu/site/login";
    private String apiUrlBase = "https://auth.sch.bme.hu/api";
    private String clientIdentifier = "testclient";
	private String clientKey = "testpass";
	
    /**
     * Token endpoint url
     * 
     * @return Default: https://auth.sch.bme.hu/oauth2/token
     */
    public String getTokenUrlBase() {
        return tokenUrlBase;
    }

    /**
     * Sets token endpoint url
     * 
     * @param tokenUrlBase (default: https://auth.sch.bme.hu/oauth2/token)
     */
    public void setTokenUrlBase(String tokenUrlBase) {
        this.tokenUrlBase = tokenUrlBase;
    }

    /**
     * Login base url
     * 
     * @return Default: https://auth.sch.bme.hu/site/login
     */
    public String getLoginUrlBase() {
        return loginUrlBase;
    }

    /**
     * Sets login base url
     * 
     * @param tokenUrlBase (default: https://auth.sch.bme.hu/site/login)
     */
    public void setLoginUrlBase(String loginUrlBase) {
        this.loginUrlBase = loginUrlBase;
    }

    /**
     * API endpoint base url
     * 
     * @return Default: https://auth.sch.bme.hu/api
     */
    public String getApiUrlBase() {
        return apiUrlBase;
    }

    /**
     * Sets API endpoint base url
     * 
     * @param tokenUrlBase (default: https://auth.sch.bme.hu/api)
     */
    public void setApiUrlBase(String apiUrlBase) {
        this.apiUrlBase = apiUrlBase;
    }

    /**
     * Sets client's identifier
     * 
     * @param clientIdentifier about 20 digit numbers
     */
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    /**
     * Sets client's secret key
     * 
     * @param clientKey about 80 chars [a-zA-Z0-9]
     */
    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    String getClientIdentifier() {
		return clientIdentifier;
	}

	String getClientKey() {
		return clientKey;
	}

    
}
