package com.example.sso.config.oauth2;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@Configuration
public class Oauth2RestTemplateConfig {

	@Bean("oauth2PasswordResttemplate")
	public OAuth2RestOperations oauth2PasswordResttemplate() {
		AccessTokenRequest atr = new DefaultAccessTokenRequest();
		OAuth2RestTemplate template = new OAuth2RestTemplate(pwdResource(), new DefaultOAuth2ClientContext(atr));
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		template.setAccessTokenProvider(provider);
		return template;
	}

	@Bean("oauth2CodeResttemplate")
	public OAuth2RestOperations oauth2CodeResttemplate() {
		AccessTokenRequest atr = new DefaultAccessTokenRequest();
		OAuth2RestTemplate template = new OAuth2RestTemplate(authorizationCodeResource(),
				new DefaultOAuth2ClientContext(atr));
		AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
		template.setAccessTokenProvider(provider);
		return template;
	}

	/**
	 * oauth2密码模式
	 * 
	 * @return
	 */
	private ResourceOwnerPasswordResourceDetails pwdResource() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientId("pwd_client");
		resource.setClientSecret("pwd_client");
		resource.setAccessTokenUri("http://127.0.0.1:8080/oauth/token");// Oauth2.0 服务端链接
		resource.setScope(Arrays.asList("user_info"));
		resource.setUsername("admin");
		resource.setPassword("admin");
		resource.setGrantType("password");// Oauth2.0 使用的模式 为密码模式
		return resource;
	}

	/**
	 * oauth2授权码模式
	 */
	private AuthorizationCodeResourceDetails authorizationCodeResource() {
		AuthorizationCodeResourceDetails codeResource = new AuthorizationCodeResourceDetails();
		codeResource.setClientId("code_client");
		codeResource.setClientSecret("code_client");
		codeResource.setScope(Arrays.asList("user_info"));
		codeResource.setAccessTokenUri("http://127.0.0.1:8080/oauth/token");
		codeResource.setPreEstablishedRedirectUri("http://127.0.0.1:8080/callBack/code");
		return codeResource;
	}

}
