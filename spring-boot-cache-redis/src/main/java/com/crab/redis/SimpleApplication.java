package com.crab.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.crab.redis.control.DataDicController;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@ServletComponentScan
public class SimpleApplication {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SimpleApplication.class, args);
		DataDicController control = context.getBean(DataDicController.class);
		control.findAllDataDic();
	}
}
