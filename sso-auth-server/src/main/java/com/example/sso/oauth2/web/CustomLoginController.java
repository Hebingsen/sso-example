package com.example.sso.oauth2.web;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sso.mapper.UserMapper;
import com.example.sso.pojo.User;

/**
 * 自定义的登录控制器
 * @作者 hebingsen
 * @时间 2018年6月28日
 */
@RestController
public class CustomLoginController {
	
	@Autowired
	private UserMapper userMapper;
	
	//@Autowired
	//private OAuth2RestTemplate oAuth2RestTemplate;
	
	@RequestMapping("/user/login")
	public Object userLogin(String username,String password) {
		
		if(Objects.isNull(username)) {
			return "用户名不能为空";
		}
		
		if(Objects.isNull(password)) {
			return "密码不能为空";
		}
		
		
		User query = new User().setPassword(password).setUsername(username);
		User user = userMapper.selectOne(query);
		
		if(Objects.isNull(user)) {
			return "该用户不存在";
		}
		
		// 进行oauth2授权登录,使用密码模式
		AccessTokenRequest atr = new DefaultAccessTokenRequest();
		ResourceOwnerPasswordResourceDetails resource = pwdResource();
		resource.setUsername(username);
		resource.setPassword(password);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(atr));
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		template.setAccessTokenProvider(provider);
		
		
		// 返回有效的access_token,
		OAuth2AccessToken accessToken = template.getAccessToken();
		String value = accessToken.getValue();
		
		// 将用户的认证信息存储到spring security中
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
				username, password);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		
		return value;
		
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
		resource.setUsername("hebingsen");
		resource.setPassword("123456");
		resource.setGrantType("password");// Oauth2.0 使用的模式 为密码模式
		return resource;
	}


}
