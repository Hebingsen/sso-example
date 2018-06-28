package com.example.sso.oauth2.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {
	
	@GetMapping("/test/user")
	public Authentication userInfo(Authentication authentication) { // 添加一个测试访问接口
		System.out.println("sso-server: user {}" +  authentication);
        return authentication;
	}
	    
}
