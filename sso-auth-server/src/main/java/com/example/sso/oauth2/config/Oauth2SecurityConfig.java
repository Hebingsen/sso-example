package com.example.sso.oauth2.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import com.example.sso.oauth2.phone.config.LoginPhoneSecurityConfig;
import com.example.sso.oauth2.userdetails.service.CustomUserDetailsService;

@EnableWebSecurity
public class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private LoginPhoneSecurityConfig loginPhoneSecurityConfig;

	// 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		int code = 1;

		switch (code) {
		case 1:
			auth.userDetailsService(customUserDetailsService).and().inMemoryAuthentication().withUser("admin")
					.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin")).roles("test");
			break;
		case 2:
			auth.userDetailsService(customUserDetailsService);
			break;
		case 3:
			super.configure(auth);
		}

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		int code = 1;
		switch (code) {
		case 1:
			super.configure(http);
			break;
		case 2:
//			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
//					.formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/form").and()
//					.authorizeRequests();

			http.apply(loginPhoneSecurityConfig);
			//registry.anyRequest().authenticated().and().csrf().disable();
			break;
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/authentication/**", "/mobile/token");
		// super.configure(web);
	}

}
