package com.bp.springboot.demosecurity.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {
	
	//add support for JDBC ... No mere Hard-Coded Users :-)
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager	= new JdbcUserDetailsManager(dataSource);
		
		// define query to retrieve a user by userName
		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"select user_id, pw, active from members where user_id=?");
		
		// define query to retrieve a authorities/role by userName
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select user_id, role from roles where user_id=?");
		
		return jdbcUserDetailsManager;
		
	}
	

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	
    	http.authorizeHttpRequests(configurer ->
    				configurer
    						.requestMatchers("/").hasRole("EMPLOYEE")
    						.requestMatchers("/leaders/**").hasRole("MANAGER")
    						.requestMatchers("/systems/**").hasRole("ADMIN")
    						.anyRequest().authenticated()
    		)
    		.formLogin(form ->
    						form
    							.loginPage("/showMyLoginPage")
    							.loginProcessingUrl("/authenticateTheUser")
    							.permitAll()
    		)
    		.logout(logout -> 
    						logout
    							.permitAll()
    		)
    				
    		.exceptionHandling(configurer ->
    						configurer.accessDeniedPage("/access-denied")
    				);
    	return http.build();
    	
    }
    
    /*
     
    // This is All Hard-Coded Users :-(
     
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails john = User.builder()
				.username("john")
				.password("{noop}test123")
				.roles("EMPLOYEE")
				.build();
		
		UserDetails mary = User.builder()
				.username("mary")
				.password("{noop}test123")
				.roles("EMPLOYEE","MANAGER")
				.build();
		
		UserDetails susan = User.builder()
				.username("susan")
				.password("{noop}test123")
				.roles("EMPLOYEE","MANAGER","ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(john, mary, susan);
	}
	*/
    
}
