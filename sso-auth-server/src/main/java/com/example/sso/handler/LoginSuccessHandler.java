package com.example.sso.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Authentication paramAuthentication)
			throws IOException, ServletException {
		System.err.println("用户登录成功----------------------");
	}

}
