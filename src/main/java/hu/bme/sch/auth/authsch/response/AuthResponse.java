package hu.bme.sch.auth.authsch.response;

import java.io.Serializable;
import java.util.List;

import hu.bme.sch.auth.authsch.struct.Scope;

@SuppressWarnings("serial")
public final class AuthResponse implements Serializable {

    private final String accessToken;
    private final long expiresIn;
    private final String tokenType;
    private final List<Scope> scopes;
    private final String refreshToken;
    
    public AuthResponse(String accessToken, long expiresIn, 
            String tokenType, List<Scope> scopes, String refreshToken) {
        
        this.accessToken = accessToken;
        this.expiresIn = System.currentTimeMillis() + (expiresIn * 1000);
        this.tokenType = tokenType;
        this.scopes = scopes;
        this.refreshToken = refreshToken;
    }

    /**
     * Used for API requests
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Timestamp of expire
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }
    
    public List<Scope> getScopes() {
        return scopes;
    }

    /**
     * Used for refreshing the access token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "AuthResponse [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", tokenType=" + tokenType
                + ", scopes=" + scopes + ", refreshToken=" + refreshToken + "]";
    }
    
}
