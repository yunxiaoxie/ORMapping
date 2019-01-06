package com.crab.common.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "audience")
@PropertySource("classpath:public/jwt.properties")
public class Audience {
	private String clientId;
	private String base64Secret;
	private String name;
	private int expiresSecond;

}
