package com.example.sso.oauth2.phone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.example.sso.oauth2.phone.filter.LoginPhoneAuthenticationFilter;
import com.example.sso.oauth2.phone.provider.LoginPhoneAuthenticationProvider;

/**
 * 手机号登录配置入口
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Component
public class LoginPhoneSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler loginPhoneSuccessHandler;
	
	@Autowired
	private LoginPhoneAuthenticationProvider loginPhoneAuthenticationProvider;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		LoginPhoneAuthenticationFilter loginByPhoneFilter = new LoginPhoneAuthenticationFilter();
		
		loginByPhoneFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		loginByPhoneFilter.setAuthenticationSuccessHandler(loginPhoneSuccessHandler);
		
		http.authenticationProvider(loginPhoneAuthenticationProvider).addFilterAfter(loginByPhoneFilter,
				UsernamePasswordAuthenticationFilter.class);
	}

}
