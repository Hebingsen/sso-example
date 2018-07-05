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

	protected Long id;
	protected String username;// 用户名
	protected String password;// 密码

}
