package com.example.sso.oauth2.phone.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.sso.oauth2.phone.token.LoginPhoneAuthenticationToken;

import lombok.Data;

/**
 * 用户手机号验证过滤器
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Data
public class LoginPhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

	private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
	private boolean postOnly = true;

	public LoginPhoneAuthenticationFilter() {
		super(new AntPathRequestMatcher("/mobile/token", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse respose)
			throws AuthenticationException, IOException, ServletException {

		if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		// 获取手机号
		String mobile = request.getParameter(mobileParameter);
		mobile = mobile == null ? "" : mobile.trim();

		// 创建认证期
		LoginPhoneAuthenticationToken loginPhoneAuthenticationToken = new LoginPhoneAuthenticationToken(mobile);

		// 设置请求详情信息
		loginPhoneAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(loginPhoneAuthenticationToken);
	}

}
