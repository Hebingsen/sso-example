package com.example.sso;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableOAuth2Sso
@SpringBootApplication
@RestController
@Slf4j
public class SsoAuthClientApplication {

	public static void main(String[] args) {
	    SpringApplication.run(SsoAuthClientApplication.class, args);
	}

    // sso测试接口
    @GetMapping("/user")
    public Authentication getUser(Authentication authentication) {
        log.info("auth : {}", authentication);
        return authentication;
    }


}
