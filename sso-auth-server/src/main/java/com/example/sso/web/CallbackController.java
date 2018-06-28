package com.example.sso.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/callBack")
public class CallbackController {
	
	@Autowired
	@Qualifier("oauth2PasswordResttemplate")
	private OAuth2RestOperations oAuth2RestOperations;
	

	/**
	 * oauth2授权码模式回调方法
	 * 
	 * @param code
	 */
	@GetMapping("/code")
	public Object callBack(String code) {
		System.err.println("code=========" + code);
		
		// 传入用户名与密码,实例化oauthResttemplate这个类,获取到这个类,然后在请求
		
		
		OAuth2AccessToken accessToken = oAuth2RestOperations.getAccessToken();
		return accessToken.getValue();
	}

}
