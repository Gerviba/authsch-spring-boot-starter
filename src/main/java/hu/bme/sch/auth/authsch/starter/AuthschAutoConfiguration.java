package hu.bme.sch.auth.authsch.starter;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.bme.sch.auth.authsch.Authsch;
import hu.bme.sch.auth.authsch.AuthschConfig;

@Configuration
@ConditionalOnClass(Authsch.class)
@EnableConfigurationProperties(AuthschProperties.class)
public class AuthschAutoConfiguration {

	@Autowired
    private AuthschProperties authschProperties;
 
    @Bean
    @ConditionalOnMissingBean
    public AuthschConfig authschConfigDefault() {
    	AuthschConfig config = new AuthschConfig();
    	
    	if (Objects.nonNull(authschProperties.getApiUrlBase()))
    		config.setApiUrlBase(authschProperties.getApiUrlBase());
    	
    	if (Objects.nonNull(authschProperties.getTokenUrlBase()))
    		config.setTokenUrlBase(authschProperties.getTokenUrlBase());
    	
    	if (Objects.nonNull(authschProperties.getLoginUrlBase()))
    		config.setLoginUrlBase(authschProperties.getLoginUrlBase());

    	if (Objects.nonNull(authschProperties.getClientIdentifier()))
    		config.setClientIdentifier(authschProperties.getClientIdentifier());
    	
    	if (Objects.nonNull(authschProperties.getClientKey()))
    		config.setClientKey(authschProperties.getClientKey());
    	
    	return config;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public Authsch authschDefault(AuthschConfig config) {
        return new Authsch(config);
    }
	
}
