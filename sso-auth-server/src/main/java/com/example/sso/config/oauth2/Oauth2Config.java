package com.example.sso.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private Oauth2CustomRedisTokenStore oauth2CustomRedisTokenStore;
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/* 配置token获取合验证时的策略 */
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		// 允许client使用form的方式进行authentication的授权
		security.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 配置oauth2的 client信息
		// authorizedGrantTypes 有4种，这里只开启2种
		// secret密码配置从 Spring Security 5.0开始必须以 {bcrypt}+加密后的密码 这种格式填写
		InMemoryClientDetailsServiceBuilder clientBuilder = clients.inMemory();

		// 授权码模式
		clientBuilder.withClient("code_client")
				.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("code_client"))
				.scopes("user_info").autoApprove(true).authorizedGrantTypes("authorization_code", "refresh_token");

		// 密码模式
		clientBuilder.withClient("pwd_client")
				.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("pwd_client"))
				.scopes("user_info").autoApprove(true).authorizedGrantTypes("password", "refresh_token");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// 1.认证管理器
		endpoints.authenticationManager(authenticationManager);
		// 2.指定token存储位置
		//endpoints.tokenStore(oauth2CustomRedisTokenStore);
		endpoints.tokenStore(jwtTokenStore());
		// 3.自定义token的生成方式
		endpoints.accessTokenConverter(accessTokenConverter());
	}
	
	@Bean
	public TokenStore jwtTokenStore() {
		JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
		return jwtTokenStore;
	}
	
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("hebingsen");
		return tokenConverter;
	}
	
	


}
