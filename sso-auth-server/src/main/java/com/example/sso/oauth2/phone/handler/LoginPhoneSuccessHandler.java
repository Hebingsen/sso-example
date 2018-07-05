package com.example.sso.oauth2.phone.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 手机号登录成功处理器
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Slf4j
@Component
public class LoginPhoneSuccessHandler implements AuthenticationSuccessHandler {

	public static final String BASIC_ = "Basic ";
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	/**
	 * 调用spring security oauth API 生成 oAuth2AccessToken
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// 获取到请求的客户端信息
		String header = request.getHeader("Authorization");
		log.info("Authorization 请求头的信息 = " + header);
		if (StringUtils.isEmpty(header) || !header.startsWith(BASIC_)) {
			throw new UnapprovedClientAuthenticationException("请求头中client信息为空");
		}

		try {
			String[] tokens = extractAndDecodeHeader(header);
			assert tokens.length == 2;
			String clientId = tokens[0];

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
			TokenRequest tokenRequest = new TokenRequest(new HashMap<String, String>(), clientId,
					clientDetails.getScope(), "mobile");
			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
			OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices
					.createAccessToken(oAuth2Authentication);
			log.info("获取token 成功：{}", oAuth2AccessToken.getValue());

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.append(objectMapper.writeValueAsString(oAuth2AccessToken));
		} catch (IOException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

	}

	/**
	 * 从header 请求中的clientId/clientsecect
	 *
	 * @param header
	 *            header中的参数
	 * @throws CheckedException
	 *             if the Basic header is not present or is not valid Base64
	 */
	public static String[] extractAndDecodeHeader(String header) throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, "utf-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new RuntimeException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
