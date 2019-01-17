package com.crab.config;

import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import com.crab.common.shiro.RestShiroFilterFactoryBean;
import com.crab.common.shiro.credential.RetryLimitHashedCredentialsMatcher;
import com.crab.common.shiro.filter.RestFormAuthenticationFilter;
import com.crab.common.shiro.filter.RestLogoutFilter;
import com.crab.common.shiro.filter.ShiroUserFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.google.common.collect.Maps;

@Configuration
public class ShiroConfig {

	/**
	 * FilterRegistrationBean
	 *
	 */
//	@Bean
//	public FilterRegistrationBean filterRegistrationBean() {
//		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//		filterRegistration.setEnabled(true);
//		filterRegistration.addUrlPatterns("/*");
//		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//		return filterRegistration;
//	}

	/**
	 * @see ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean bean = new RestShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/login");
		bean.setUnauthorizedUrl("/401");
		//config filter chain
		Map<String, Filter> filters = bean.getFilters();
		filters.put("user", new ShiroUserFilter());
		filters.put("authc", new RestFormAuthenticationFilter());
		filters.put("perms", new RestAuthorizationFilter());
		filters.put("logout", new RestLogoutFilter());
		//配置filters
//		Map<String, Filter> filters = Maps.newHashMap();
//		filters.put("perms", new URLPermissionsFilter());
//		filters.put("anon", new AnonymousFilter());
//		bean.setFilters(filters);
		// 配置过滤器链
        //LinkHashMap是有序的，shiro会根据添加的顺序进行拦截
		Map<String, String> chains = Maps.newLinkedHashMap();
		//for swagger
		chains.put("/swagger-ui.html", "anon");
		chains.put("/static/**", "anon");
		chains.put("/swagger/**","anon");
		chains.put("/webjars/**", "anon");
		chains.put("/swagger-resources/**","anon");
		chains.put("/v2/**","anon");

		chains.put("/course/**", "authc");
		chains.put("/user/**", "authc");
		chains.put("/logout", "logout");

		chains.put("/login/**", "anon");
		chains.put("/css/**", "anon");
		chains.put("/fonts/**", "anon");
		chains.put("/layer/**", "anon");
		//过滤连接自定义，从上往下顺序执行，所以用LinkHashMap /**放在最下边
		chains.put("/**", "authc,perms");
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	}

	/**
	 * @see org.apache.shiro.mgt.SecurityManager
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm());
		manager.setCacheManager(cacheManager());
		manager.setSessionManager(defaultWebSessionManager());
		return manager;
	}

	/**
	 * @see DefaultWebSessionManager
	 */
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}

	/**
	 * 自定义认证类，继承AuthorizingRealm，负责用户的认证和权限处理
	 * @see UserRealm--->AuthorizingRealm
	 * @return
	 */
	@Bean
	@DependsOn(value = "lifecycleBeanPostProcessor")
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
//		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		userRealm.setCacheManager(cacheManager());
		return userRealm;
	}


	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:public/ehcache.xml");
		return cacheManager;
	}

//	@Bean
//	public HashedCredentialsMatcher hashedCredentialsMatcher() {
//		// 重试次数
//		return new RetryLimitHashedCredentialsMatcher("md5");
//	}

	//for thymeleaf-extras-shiro
//	@Bean
//	public ShiroDialect shiroDialect() {
//		return new ShiroDialect();
//	}

	/**
     * shiro生命周期
     */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}