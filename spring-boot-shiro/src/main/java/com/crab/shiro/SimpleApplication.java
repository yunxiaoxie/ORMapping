package com.crab.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Configuration
//@Import({
//	DataSourceAutoConfiguration.class,
//	DataSourceTransactionManagerAutoConfiguration.class,
//	DispatcherServletAutoConfiguration.class,
//	EmbeddedServletContainerAutoConfiguration.class,
//	ErrorMvcAutoConfiguration.class,
//	HttpEncodingAutoConfiguration.class,
//	HttpMessageConvertersAutoConfiguration.class,
//	JacksonAutoConfiguration.class,
//	JmxAutoConfiguration.class,
//	MultipartAutoConfiguration.class,
//	MybatisAutoConfiguration.class,
//	PersistenceExceptionTranslationAutoConfiguration.class,
//	PropertyPlaceholderAutoConfiguration.class,
//	ServerPropertiesAutoConfiguration.class,
//	SpringDataWebAutoConfiguration.class,
//	TransactionAutoConfiguration.class,
//	WebMvcAutoConfiguration.class,
//	WebSocketAutoConfiguration.class,
//	
//})
@MapperScan("com.crab.shiro.mapper")
public class SimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleApplication.class, args);
	}
}
