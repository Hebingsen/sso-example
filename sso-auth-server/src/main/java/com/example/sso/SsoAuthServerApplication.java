package com.example.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import tk.mybatis.spring.annotation.MapperScan;

@EnableAuthorizationServer
@SpringBootApplication
@MapperScan(basePackages = "com.example.sso.mapper")
public class SsoAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoAuthServerApplication.class, args);
	}
	

}
