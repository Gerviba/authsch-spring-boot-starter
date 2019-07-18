package hu.bme.sch.auth.authsch.login;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hu.bme.sch.auth.authsch.Authsch;

@Configuration
@ConditionalOnClass(Authsch.class)
@EnableConfigurationProperties(AuthschLoginProperties.class)
@ComponentScan(basePackageClasses = AuthschDefaultController.class)
public class AuthschLoginAutoConfiguration {
	
	@Autowired
    private AuthschLoginProperties authschLoginProperties;
 
    @Bean
    @ConditionalOnMissingBean
    public AuthschLoginConfig authschLoginConfigDefault() {
    	AuthschLoginConfig config = new AuthschLoginConfig();
    	
    	if (Objects.nonNull(authschLoginProperties.getLoginOk()))
    		config.setLoginOk(authschLoginProperties.getLoginOk());
    	
    	if (Objects.nonNull(authschLoginProperties.getLoginFailed()))
    		config.setLoginFailed(authschLoginProperties.getLoginFailed());
    	
    	if (Objects.nonNull(authschLoginProperties.getUnexpectedError()))
    		config.setUnexpectedError(authschLoginProperties.getUnexpectedError());
    	
    	if (Objects.nonNull(authschLoginProperties.getLoggedOut()))
    		config.setLoggedOut(authschLoginProperties.getLoggedOut());
    	
    	if (Objects.nonNull(authschLoginProperties.getSessionAttributeName()))
    		config.setSessionAttributeName(authschLoginProperties.getSessionAttributeName());

    	if (Objects.nonNull(authschLoginProperties.getSaltForUniqueId()))
    		config.setSaltForUniqueId(authschLoginProperties.getSaltForUniqueId());
    	
    	return config;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public AuthschLoginLogicSupplier authschLoginSupplierDefault() {
    	return new AuthschLoginLogicSupplier();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public AuthschLoginService authschLoginDefault() {
        return new AuthschLoginService();
    }
	
}
