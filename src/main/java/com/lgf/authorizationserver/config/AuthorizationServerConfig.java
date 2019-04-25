package com.lgf.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${redirect_uri}")
	private String redirect_uri = "http://example.com"; 
	
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	/** WORKARROUND
	 * SE AGREGA ESTO PARA PODER PEDIR EL TOKEN CON GRANT TYPE PASSWORD 
	 *  https://github.com/spring-projects/spring-security-oauth/issues/1328
	 *  // TODO: VER si es necesario este tipo de Flow y sino sacarlo
	 * **/
	
	private AuthenticationManager authenticationManagerBean;

	@Autowired
	public void setAuthenticationManagerBean(AuthenticationManager authenticationManagerBean) {
		this.authenticationManagerBean = authenticationManagerBean;
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManagerBean);
	}
	/** FIN WORKARROUND **/ 
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("tracker-support")
				.secret("tracker-support")
				.authorizedGrantTypes("implicit","password", "authorization_code", "refresh_token", "client_credentials")
				.redirectUris(redirect_uri)
				.autoApprove(true)
				.scopes("home");
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.checkTokenAccess("isAuthenticated()");
	}

}
