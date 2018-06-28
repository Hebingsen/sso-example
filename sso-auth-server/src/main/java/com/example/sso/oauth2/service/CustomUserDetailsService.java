package com.example.sso.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import com.example.sso.mapper.UserMapper;
import com.example.sso.pojo.SecurityUser;
import com.example.sso.pojo.User;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userMapper.selectOne(new User().setUsername(username));
		if (user == null) {
			throw new UsernameNotFoundException("admin: " + username + " do not exist!");
		}

		SecurityUser securityUser = new SecurityUser();
		securityUser.setUsername(user.getUsername());
		securityUser.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));

		return securityUser;
	}

}
