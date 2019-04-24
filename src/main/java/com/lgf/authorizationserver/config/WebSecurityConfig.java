package com.lgf.authorizationserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity(debug=true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	  public void configure(AuthenticationManagerBuilder builder)
	          throws Exception {
	      builder.inMemoryAuthentication()
	             .withUser("lfavaro")
	             .password("lfavaro")
	             .roles("TALL");
	  }
}
