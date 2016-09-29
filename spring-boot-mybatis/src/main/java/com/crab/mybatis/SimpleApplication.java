package com.crab.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.crab.mybatis.config.Config;

@SpringBootApplication
public class SimpleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SimpleApplication.class, args);
		Config config = ctx.getBean(Config.class);
		System.out.println(config.getFilenames());
	}
}
