package com.example.sso.pojo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Accessors(chain = true)
public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Collection<? extends GrantedAuthority> authorities;
	
	public SecurityUser() {
		super();
	}

	public SecurityUser(Collection<? extends GrantedAuthority> authorities) {
		super();
		this.authorities = authorities;
	}

	

	/**
	 * 查询并返回权限信息
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	// 账户是否未过期
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	// 账户是否未锁定
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	// 密码是否未过期
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 账户是否激活
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

}
