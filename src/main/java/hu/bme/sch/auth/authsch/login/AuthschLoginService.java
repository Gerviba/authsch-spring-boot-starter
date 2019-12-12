package hu.bme.sch.auth.authsch.login;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import hu.bme.sch.auth.authsch.Authsch;
import hu.bme.sch.auth.authsch.exception.AuthschAuthenticationFailedException;
import hu.bme.sch.auth.authsch.exception.AuthschInvalidStateException;
import hu.bme.sch.auth.authsch.response.AuthResponse;
import hu.bme.sch.auth.authsch.response.ProfileDataResponse;

public class AuthschLoginService {

	@Autowired
	private Authsch authSch;
	
	@Autowired
	private AuthschLoginLogicSupplier methods;
	
	@Autowired
	private AuthschLoginConfig config;

	/**
	 * Handler for the login webhook
	 * @param code Unique code
	 * @param state State of the auth
	 * @param request HttpServletRequest
	 * @return Was it successful
	 * @throws AuthschInvalidStateException
	 * @throws AuthschAuthenticationFailedException
	 */
	public boolean handleLoginHook(String code, String state, HttpServletRequest request) 
			throws AuthschInvalidStateException, AuthschAuthenticationFailedException {
		
		if (!buildUniqueState(request).equals(state))
			throw new AuthschInvalidStateException();

		Authentication auth = null;
		try {
			AuthResponse response = authSch.validateAuthentication(code);
			ProfileDataResponse profile = authSch.getProfile(response.getAccessToken());

			AuthschUser user = methods.getUserExists().test(profile) 
					? methods.getLoadUser().apply(profile) 
					: methods.getCreateUser().apply(profile);
					
			user.updateUser(profile);
			auth = new UsernamePasswordAuthenticationToken(profile.getInternalId(), state, getAuthorities(profile, user));
			
			request.getSession().setAttribute(config.getSessionAttributeName(), user);
			SecurityContextHolder.getContext().setAuthentication(auth);

		} catch (Exception e) {
			if (auth != null)
				auth.setAuthenticated(false);
			throw new AuthschAuthenticationFailedException(e);
		}

		return auth != null && auth.isAuthenticated();
	}
	
	/**
	 * @param request HttpServletRequest
	 * @return A unique url to redirect the user to
	 */
	public String generateLoginRedirectUrl(HttpServletRequest request) {
		return authSch.generateLoginUrl(buildUniqueState(request), methods.getRequestScopes());
	}
	
	/**
	 * Destroys the session
	 * @param request
	 */
	public void logout(HttpServletRequest request) {
		request.removeAttribute(config.getSessionAttributeName());
		request.getSession().removeAttribute(config.getSessionAttributeName());
		request.changeSessionId();
	}

	private List<GrantedAuthority> getAuthorities(ProfileDataResponse profile, AuthschUser user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (String role : user.getRoles())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		
		for (String role : methods.getResolveRoles().apply(profile, user))
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		
		return authorities;
	}

	private String buildUniqueState(HttpServletRequest request) {
		return hashString(request.getSession().getId() + request.getLocalAddr() + config.getSaltForUniqueId());
	}

	private static final String hashString(String in) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return String.format("%064x", new BigInteger(1, digest.digest(in.getBytes())));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
