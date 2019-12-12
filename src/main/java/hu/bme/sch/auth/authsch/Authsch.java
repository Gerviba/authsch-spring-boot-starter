package hu.bme.sch.auth.authsch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.bme.sch.auth.authsch.exception.AuthschResponseException;
import hu.bme.sch.auth.authsch.response.AuthResponse;
import hu.bme.sch.auth.authsch.response.ProfileDataResponse;
import hu.bme.sch.auth.authsch.response.ProfileDataResponse.ProfileDataResponseBuilder;
import hu.bme.sch.auth.authsch.struct.Scope;

/**
 * Authsch API service
 * 
 * @author Gerviba
 * @see {@link #validateAuthentication(String)}
 * @see {@link #refreshToken(String)}
 * @see {@link #getProfile(String)}
 */
@SuppressWarnings("serial")
public class Authsch implements Serializable {

    private AuthschConfig config;

    public Authsch() {
    	this.config = new AuthschConfig();
    }
    
    public Authsch(AuthschConfig config) {
    	this.config = config;
    }

    /**
     * @see AuthschConfig
     */
    public AuthschConfig getConfig() {
    	return config;
    }
    
    /**
     * Validate authentication
     * 
     * @param code Received `code` value from HTTPS parameters
     * @throws AuthschResponseException
     */
    public AuthResponse validateAuthentication(String code) {
        return httpPost("grant_type=authorization_code&code=" + code, false);
    }

    /**
     * Validate authentication
     * 
     * @param code Received `code` value from HTTPS parameters
     * @throws AuthschResponseException
     */
    public AuthResponse refreshToken(String refreshToken) {
        return httpPost("grant_type=refresh_token&refresh_token=" + refreshToken, true);
    }

    /**
     * Login URL generator
     * 
     * @param uniqueId A unique identifier for the user. Must be hashed! (eg. sha256(JSESSIONID))
     * @param scopes A list of used scopes
     * @return Generated login url
     */
    public String generateLoginUrl(String uniqueId, List<Scope> scopes) {
        return String.format("%s?response_type=code&client_id=%s&state=%s&scope=%s",
                config.getLoginUrlBase(), config.getClientIdentifier(), uniqueId, Scope.buildForUrl(scopes));
    }

    /**
     * Login URL generator
     * 
     * @param uniqueId A unique identifier for the user. Must be hashed! (eg. sha256(JSESSIONID))
     * @param scopes A list of used scopes
     * @return Generated login url
     */
    public String generateLoginUrl(String uniqueId, Scope... scopes) {
        return String.format("%s?response_type=code&client_id=%s&state=%s&scope=%s",
        		config.getLoginUrlBase(), config.getClientIdentifier(), uniqueId, Scope.buildForUrl(scopes));
    }

    /**
     * Load profile info
     * 
     * @param accessToken
     * @throws AuthschResponseException
     */
    public ProfileDataResponse getProfile(String accessToken) {
        return httpGet("profile", accessToken);
    }

    private ProfileDataResponse httpGet(String service, String accessToken) {
        URL obj = newUrl(config.getApiUrlBase() + "/" + service + "/?access_token=" + accessToken);
        HttpsURLConnection con = newGetConnection(obj);
        setGetHeaders(con);
        processResponseCode(con);

        return mapProfileDataResponse(readData(con));
    }

    private HttpsURLConnection newGetConnection(URL obj) {
        HttpsURLConnection con;
        try {
            con = (HttpsURLConnection) obj.openConnection();
        } catch (IOException e) {
            throw new AuthschResponseException("Failed to open connection", e);
        }
        return con;
    }

    private void setGetHeaders(HttpsURLConnection con) {
        con.setRequestProperty("User-Agent", System.getProperty("authsch.useragent", "AuthSchJavaAPI"));
        con.setRequestProperty("Accept", "application/json");
    }

    ProfileDataResponse mapProfileDataResponse(String rawJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode obj = objectMapper.readTree(rawJson);
            
            ProfileDataResponseBuilder response = ProfileDataResponse.newBuilder();

            for (Scope scope : Scope.values())
                if (scope.canApply(obj))
                    scope.apply(response, obj);
            
            return response.build();
        } catch (NullPointerException | IOException e) {
            throw new AuthschResponseException("Failed to parse auth response", e);
        }
    }

    private AuthResponse httpPost(String parameters, boolean reAuth) {
        URL obj = newUrl(config.getTokenUrlBase());
        HttpsURLConnection con = newPostConnection(obj);
        setPostHeaders(con);
        writePostParameters(parameters, con);
        processResponseCode(con);
        String response = readData(con);

        return reAuth
                ? mapAuthResponse(response, parameters)
                : mapAuthResponse(response);
    }

    private URL newUrl(String url) {
        URL obj;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            throw new AuthschResponseException("Failed to create new URL", e);
        }
        return obj;
    }

    private HttpsURLConnection newPostConnection(URL obj) {
        HttpsURLConnection con;
        try {
            con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
        } catch (IOException e) {
            throw new AuthschResponseException("Failed to open connection", e);
        }
        return con;
    }

    private void setPostHeaders(HttpsURLConnection con) {
        con.setRequestProperty("User-Agent", System.getProperty("authsch.useragent", "AuthSchJavaAPI"));
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString((config.getClientIdentifier() + ":" + config.getClientKey()).getBytes()));

        con.setDoOutput(true);
    }

    private void writePostParameters(String parameters, HttpsURLConnection con) {
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(parameters);
            wr.flush();
        } catch (IOException e) {
            throw new AuthschResponseException("Failed to write post data", e);
        }
    }

    private String readData(HttpsURLConnection con) {
        String inputLine, response = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            while ((inputLine = in.readLine()) != null)
                response += inputLine;
        } catch (IOException e) {
            throw new AuthschResponseException("Failed to write post data", e);
        }
        return response;
    }

    private int processResponseCode(HttpsURLConnection con) {
        int responseCode = -1;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            throw new AuthschResponseException("Failed to get response code", e);
        }

        if (responseCode != 200)
            throw new AuthschResponseException("HTTP response code: " + responseCode);
        return responseCode;
    }

    AuthResponse mapAuthResponse(String rawJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode obj = objectMapper.readTree(rawJson);
            return new AuthResponse(
                    obj.get("access_token").asText(),
                    obj.get("expires_in").asLong(),
                    obj.get("token_type").asText(),
                    Scope.listFromString(" ", obj.get("scope").asText()),
                    obj.get("refresh_token").asText());
        } catch (NullPointerException | IOException e) {
            throw new AuthschResponseException("Failed to parse auth response", e);
        }
    }

    AuthResponse mapAuthResponse(String rawJson, String parameters) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode obj = objectMapper.readTree(rawJson);
            return new AuthResponse(
                    obj.get("access_token").asText(),
                    obj.get("expires_in").asLong(),
                    obj.get("token_type").asText(),
                    Scope.listFromString(" ", obj.get("scope").asText()),
                    parameters.substring(parameters.lastIndexOf('=') + 1));
        } catch (NullPointerException | IOException e) {
            throw new AuthschResponseException("Failed to parse auth response", e);
        }
    }

}
