package com.crab.common.vo;

import lombok.Data;

@Data
public class AccessToken {
	private String token;
	private String bearer;
	private long expires_in;
	private String introduction;
	private String avatar="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";

}