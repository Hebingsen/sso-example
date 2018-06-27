package com.example.sso.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String access_token;
	private String token_type;
	private String refresh_token;
	private Long expires_in;
	private String scope;

}
