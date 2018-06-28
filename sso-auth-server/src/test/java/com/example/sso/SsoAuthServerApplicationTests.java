package com.example.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.sso.mapper.UserMapper;
import com.example.sso.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsoAuthServerApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	UserMapper userMapper;

	@Test
	public void testRedis() throws Exception {
		// 保存字符串
		stringRedisTemplate.opsForValue().set("aaa", "111");
		//Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }
	
	@Test
	public void testMapper() throws Exception {
		
		User user = new User();
		user.setUsername("hebingsen");
		user.setPassword("123456");
		int insert = userMapper.insert(user);
		
		System.out.println(insert);
		
		
		
	}

}
