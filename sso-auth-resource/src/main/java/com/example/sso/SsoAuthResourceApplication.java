package com.example.sso;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableResourceServer
@SpringBootApplication
@RestController
public class SsoAuthResourceApplication {
	
	//@Autowired
	//private OAuth2RestOperations oAuth2RestOperations;

	public static void main(String[] args) {
		SpringApplication.run(SsoAuthResourceApplication.class, args);
	}

    // 添加一个测试访问接口
    @GetMapping("/user")
    public Authentication getUser(Authentication authentication) {
    	
    		OAuth2RestOperations restTemplate = restTemplate();
    		OAuth2AccessToken accessToken = restTemplate.getAccessToken();
    		
    		System.err.println("accessToken = " + accessToken.getTokenType());
    	
        log.info("resource: user {}", authentication);
        return authentication;
    }
    
    @Bean
    public OAuth2RestOperations restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        template.setAccessTokenProvider(provider);
        return template;
    }

    private ResourceOwnerPasswordResourceDetails resource() {
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


}
