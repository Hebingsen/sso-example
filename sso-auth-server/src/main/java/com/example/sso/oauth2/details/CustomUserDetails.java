package com.example.sso.oauth2.details;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sso.pojo.User;

import lombok.Builder;

/**
 * 自定义用户详情
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Builder
public class CustomUserDetails extends User implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	public CustomUserDetails(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public CustomUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
