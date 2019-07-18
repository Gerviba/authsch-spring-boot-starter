package hu.bme.sch.auth.authsch.login;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import hu.bme.sch.auth.authsch.response.ProfileDataResponse;
import hu.bme.sch.auth.authsch.struct.Scope;

public class AuthschLoginLogicSupplier {

	private List<Scope> requestScopes = new ArrayList<>();
	
	private Predicate<ProfileDataResponse> userExists = profile -> 
			{ throw new RuntimeException("Procedure to `userExists` was not supplied"); };
			
	private Function<ProfileDataResponse, AuthschUser> loadUser = profile -> 
			{ throw new RuntimeException("Procedure to `loadUser` was not supplied"); };
			
	private Function<ProfileDataResponse, AuthschUser> createUser = profile -> 
			{ throw new RuntimeException("Procedure to `createUser` was not supplied"); };
			
	private BiFunction<ProfileDataResponse, AuthschUser, List<String>> resolveRoles = (profile, user) -> 
			{ throw new RuntimeException("Procedure to `resolveRoles` was not supplied"); };

	List<Scope> getRequestScopes() {
		return requestScopes;
	}

	public AuthschLoginLogicSupplier withRequestScopes(List<Scope> requestScopes) {
		this.requestScopes = requestScopes;
		return this;
	}

	public AuthschLoginLogicSupplier withRequestScopes(Scope... scopes) {
		this.requestScopes = new ArrayList<>();
		for (Scope s : scopes)
			this.requestScopes.add(s);
		
		return this;
	}

	Predicate<ProfileDataResponse> getUserExists() {
		return userExists;
	}

	public AuthschLoginLogicSupplier withUserExistsMethod(Predicate<ProfileDataResponse> userExists) {
		this.userExists = userExists;
		return this;
	}

	Function<ProfileDataResponse, AuthschUser> getLoadUser() {
		return loadUser;
	}

	public AuthschLoginLogicSupplier withLoadUserMethod(Function<ProfileDataResponse, AuthschUser> loadUser) {
		this.loadUser = loadUser;
		return this;
	}

	Function<ProfileDataResponse, AuthschUser> getCreateUser() {
		return createUser;
	}

	public AuthschLoginLogicSupplier withCreateUserMethod(Function<ProfileDataResponse, AuthschUser> createUser) {
		this.createUser = createUser;
		return this;
	}

	BiFunction<ProfileDataResponse, AuthschUser, List<String>> getResolveRoles() {
		return resolveRoles;
	}

	public AuthschLoginLogicSupplier withResolveRolesMethod(BiFunction<ProfileDataResponse, AuthschUser, List<String>> resolveRoles) {
		this.resolveRoles = resolveRoles;
		return this;
	}
	
}
