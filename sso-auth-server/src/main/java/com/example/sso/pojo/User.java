package com.example.sso.pojo;

import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Table(name = "user")
@Data
@ToString
@Accessors(chain = true)
public class User {

	private Long id;
	private String username;// 用户名
	private String password;// 密码

}
