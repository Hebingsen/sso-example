package com.example.sso.oauth2.userdetails.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sso.mapper.UserMapper;
import com.example.sso.oauth2.details.CustomUserDetails;
import com.example.sso.pojo.User;

/**
 * 自定义用户详情service业务层
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (Objects.isNull(username)) {
			throw new IllegalArgumentException("Illegal Argument : username ");
		}

		User param = new User();
		param.setUsername(username);

		User user = userMapper.selectOne(param);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("User Is Not Found : username = " + username);
		}

		CustomUserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}

}
