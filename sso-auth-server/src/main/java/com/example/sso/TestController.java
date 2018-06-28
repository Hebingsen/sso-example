package com.example.sso;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/test")
	public Object test() {
		return "恭喜你登录成功啦,有权限访问了";
	}

}
