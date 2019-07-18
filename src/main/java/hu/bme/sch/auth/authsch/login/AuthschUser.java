package hu.bme.sch.auth.authsch.login;

import java.util.List;

import hu.bme.sch.auth.authsch.response.ProfileDataResponse;

public interface AuthschUser {

	public List<String> getRoles();
	
	public default void updateUser(ProfileDataResponse profile) {}; 
	
}
