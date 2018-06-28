package com.example.sso.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, AuthenticationException paramAuthenticationException)
			throws IOException, ServletException {
		System.err.println("用户登录失败----------------------" + paramAuthenticationException.getMessage());
		PrintWriter writer = paramHttpServletResponse.getWriter();
		writer.println("登录失败,msg = " + paramAuthenticationException.getMessage());
		writer.flush();
		writer.close();

	}

}
