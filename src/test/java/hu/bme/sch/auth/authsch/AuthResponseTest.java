package hu.bme.sch.auth.authsch;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import hu.bme.sch.auth.authsch.Authsch;
import hu.bme.sch.auth.authsch.exception.AuthschResponseException;
import hu.bme.sch.auth.authsch.response.AuthResponse;
import hu.bme.sch.auth.authsch.struct.Scope;

public class AuthResponseTest {
    
    @Test
    public void testMapperAuth() throws Exception {
        Authsch api = new Authsch();
        AuthResponse auth = api.mapAuthResponse("{\"access_token\":\"5ebc7d15fd3b9968aee4d5527b585b479f263133\","
                + "\"expires_in\":3600,\"token_type\":\"Bearer\","
                + "\"scope\":\"basic displayName niifEduPersonAttendedCourse bmeunitscope\","
                + "\"refresh_token\":\"8163672db8553b6f9b39e57c41b5c57811228a2d\"}");
        
        assertEquals("5ebc7d15fd3b9968aee4d5527b585b479f263133", auth.getAccessToken());
        assertTrue(auth.getExpiresIn() <= System.currentTimeMillis() + (3600 * 1000));
        assertEquals("Bearer", auth.getTokenType());
        assertEquals(Arrays.asList(Scope.BASIC, Scope.DISPLAY_NAME, Scope.COURSES, Scope.BME_UNIT_SCOPE), 
                auth.getScopes());
        assertEquals("8163672db8553b6f9b39e57c41b5c57811228a2d", auth.getRefreshToken());
    }

    @Test(expected = AuthschResponseException.class)
    public void testMapperAuthNull() throws Exception {
        new Authsch().mapAuthResponse("{"
                + "\"expires_in\":3600,\"token_type\":\"Bearer\","
                + "\"scope\":\"basic displayName niifEduPersonAttendedCourse bmeunitscope\","
                + "\"refresh_token\":\"8163672db8553b6f9b39e57c41b5c57811228a2d\"}");
    }
    
    @Test
    public void testMapperReauth() throws Exception {
        Authsch api = new Authsch();
        AuthResponse auth = api.mapAuthResponse("{\"access_token\":\"5ebc7d15fd3b9968aee4d5527b585b479f263133\","
                + "\"expires_in\":3600,\"token_type\":\"Bearer\","
                + "\"scope\":\"basic displayName niifEduPersonAttendedCourse bmeunitscope\"}",
                "grant_type=refresh_token&refresh_token=8163672db8553b6f9b39e57c41b5c57811228a2d");
        
        assertEquals("5ebc7d15fd3b9968aee4d5527b585b479f263133", auth.getAccessToken());
        assertTrue(auth.getExpiresIn() <= System.currentTimeMillis() + (3600 * 1000));
        assertEquals("Bearer", auth.getTokenType());
        assertEquals(Arrays.asList(Scope.BASIC, Scope.DISPLAY_NAME, Scope.COURSES, Scope.BME_UNIT_SCOPE), 
                auth.getScopes());
        assertEquals("8163672db8553b6f9b39e57c41b5c57811228a2d", auth.getRefreshToken());
    }

    @Test(expected = AuthschResponseException.class)
    public void testMapperReauthNull() throws Exception {
        new Authsch().mapAuthResponse("{"
                + "\"expires_in\":3600,\"token_type\":\"Bearer\","
                + "\"scope\":\"basic displayName niifEduPersonAttendedCourse bmeunitscope\","
                + "\"refresh_token\":\"8163672db8553b6f9b39e57c41b5c57811228a2d\"}",
                "grant_type=refresh_token&refresh_token=8163672db8553b6f9b39e57c41b5c57811228a2d");
    }
}
