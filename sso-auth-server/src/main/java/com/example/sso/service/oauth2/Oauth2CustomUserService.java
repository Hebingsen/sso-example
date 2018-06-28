package com.example.sso.service.oauth2;

import java.util.Objects;

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
public class Oauth2CustomUserService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("当前登录用户username = " + username);

		User user = userMapper.selectOne(new User().setUsername(username));
		System.out.println("从数据库查询的用户信息 : " + user);

		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("该用户不存在！");
		}

		SecurityUser securityUser = new SecurityUser();
		securityUser.setId(user.getId());
		securityUser.setUsername(user.getUsername());
		String encodePassword = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword());
		System.out.println("加密后的密码 : " + encodePassword);
		securityUser.setPassword(encodePassword);

		return securityUser;
	}

}
