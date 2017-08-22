package com.crab.mybatis.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix="crab")
public class FileUploadConfig {
		private String uploadUrl;
		private String userName;
		private String password;
		
		
	 	public String getUploadUrl() {
			return uploadUrl;
		}


		public void setUploadUrl(String uploadUrl) {
			this.uploadUrl = uploadUrl;
		}


		public String getUserName() {
			return userName;
		}


		public void setUserName(String userName) {
			this.userName = userName;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		@Bean
	    public MultipartConfigElement multipartConfigElement() {
	        MultipartConfigFactory factory = new MultipartConfigFactory();
	        factory.setMaxFileSize(500*1024L * 1024L);
	        return factory.createMultipartConfig();
	    }
}
