package com.example.sso.oauth2.phone.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import com.example.sso.mapper.UserMapper;
import com.example.sso.oauth2.details.CustomUserDetails;
import com.example.sso.oauth2.phone.token.LoginPhoneAuthenticationToken;
import com.example.sso.pojo.User;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用手机号登录的时候的权限校验器
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Slf4j
@Component
public class LoginPhoneAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		LoginPhoneAuthenticationToken loginPhoneAuthenticationToken = (LoginPhoneAuthenticationToken) authentication;
		User user = findByUsername((String) loginPhoneAuthenticationToken.getPrincipal());
		CustomUserDetails userDetails = buildUserDetails(user);

		// 设置 principal
		LoginPhoneAuthenticationToken authenticationToken = new LoginPhoneAuthenticationToken(userDetails, null);
		// 设置 details
		authenticationToken.setDetails(userDetails);

		return authenticationToken;
	}

	private CustomUserDetails buildUserDetails(User user) {
		CustomUserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}

	private User findByUsername(String username) {
		User user = userMapper.selectOne(new User().setUsername(username));
		return user;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return LoginPhoneAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
