package com.example.sso.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import com.example.sso.handler.LoginFailureHandler;
import com.example.sso.handler.LoginSuccessHandler;
import com.example.sso.provider.SecurityProvider;
import com.example.sso.service.oauth2.Oauth2CustomUserService;

@EnableWebSecurity
public class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Oauth2CustomUserService userDetailsService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private LoginFailureHandler loginFailureHandler;
	
	@Autowired
	private SecurityProvider securityProvider;
	
	

    // 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    		//auth.userDetailsService(userDetailsService);
    		//auth.authenticationProvider(securityProvider);
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"))
                .roles("test");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic().disable();
		
		// 自定义的登录接口
		//http.formLogin().loginProcessingUrl("/user/login").successHandler(loginSuccessHandler)
		//http.formLogin().successHandler(loginSuccessHandler)
		
		// loginPage 自定义登录路径
//		http.formLogin().loginPage("/user/login").successHandler(loginSuccessHandler)
//		.failureHandler(loginFailureHandler)
//		.and()
//		.authorizeRequests()
//		.antMatchers("/user/login").permitAll()
//		.anyRequest().authenticated();
		
		super.configure(http);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		web.ignoring().antMatchers("/callBack/**");
	}
    
    
}
