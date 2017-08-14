package com.crab.mybatis.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:public/myapplication.yml")
@ConfigurationProperties(prefix = "ignored")
public class Config {
	private String name;
	private List<String> filenames;

	public List<String> getFilenames() {
		return filenames;
	}

	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
