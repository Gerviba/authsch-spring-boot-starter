package hu.bme.sch.auth.authsch;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import hu.bme.sch.auth.authsch.Authsch;
import hu.bme.sch.auth.authsch.struct.Scope;

public class AuthschTest {

    @Test
    public void testUrlGenerationAll() throws Exception {
        Authsch api = new Authsch();
        api.getConfig().setClientIdentifier("cid");
        assertEquals("https://auth.sch.bme.hu/site/login?response_type=code"
                + "&client_id=cid&state=unique&scope=basic+displayName+sn"
                + "+givenName+mail+niifPersonOrgID+linkedAccounts+eduPersonEntitlement"
                + "+roomNumber+mobile+niifEduPersonAttendedCourse+entrants+admembership"
                + "+bmeunitscope", api.generateLoginUrl("unique", Scope.values()));
    }

    @Test
    public void testUrlGenerationSpecified() throws Exception {
        Authsch api = new Authsch();
        assertEquals("https://auth.sch.bme.hu/site/login?response_type=code&client_id=testclient"
                + "&state=unique&scope=basic+givenName+mail+bmeunitscope", 
                api.generateLoginUrl("unique", Scope.BASIC, Scope.GIVEN_NAME, Scope.MAIL, Scope.BME_UNIT_SCOPE));
    }
    
    @Test
    public void testUrlGenerationFormList() throws Exception {
        Authsch api = new Authsch();
        List<Scope> scopes = Arrays.asList(Scope.BASIC, Scope.GIVEN_NAME, Scope.MAIL, Scope.COURSES);
        assertEquals("https://auth.sch.bme.hu/site/login?response_type=code&client_id=testclient"
                + "&state=unique&scope=basic+givenName+mail+niifEduPersonAttendedCourse", 
                api.generateLoginUrl("unique", scopes));
    }
    
    @Test
    public void testSettersGetters() throws Exception {
        Authsch api = new Authsch();
        api.getConfig().setApiUrlBase("a");
        assertEquals("a", api.getConfig().getApiUrlBase());
        api.getConfig().setLoginUrlBase("b");
        assertEquals("b", api.getConfig().getLoginUrlBase());
        api.getConfig().setTokenUrlBase("c");
        assertEquals("c", api.getConfig().getTokenUrlBase());
    }
    
}
