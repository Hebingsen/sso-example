package com.example.sso.oauth2.phone.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 通过手机号登录的token令牌,后续可以拓展为多个登录token令牌,例如使用微信登录,账号密码登录等等
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
public class LoginPhoneAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	public LoginPhoneAuthenticationToken(String phone) {
		super(null);
		this.principal = phone;
		// 是否将此令牌设置为受信任
		setAuthenticated(false);
	}

	public LoginPhoneAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}

}
