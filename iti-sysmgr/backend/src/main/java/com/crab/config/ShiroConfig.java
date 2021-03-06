package com.crab.config;

import java.util.Map;

import javax.servlet.Filter;

import com.crab.common.shiro.RestShiroFilterFactoryBean;
import com.crab.common.shiro.filter.*;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.google.common.collect.Maps;

@Configuration
public class ShiroConfig {

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
		filters.put("authc", new RestAuthenticationFilter());
		filters.put("roles", new RestRoleFilter());
		filters.put("perms", new RestAuthorizationFilter());
		filters.put("login", new RestLoginFilter());
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

		//添加url==method
		//注：这里配置的role,perm会走Filter，但注解的不会走，可以单独配置注解
		//直接在方法上配置注解更灵活
		//这里的url可以尽量写的不同，如/course/add, /course/list, /course/del
		//若url一定要相同，那就要加上method以区别对待，如/course===GET, /course===POST, /course===DELETE
		chains.put("/course/list==Get", "authc");
		chains.put("/course/chapter/**", "authc");
		chains.put("/course/**", "authc,roles[teacher],perms[Export]");
		chains.put("/teacher/**", "authc");
		chains.put("/student/**", "authc");
		chains.put("/user/**", "authc");
		chains.put("/logout", "logout");
		chains.put("/login/**", "login");

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
		manager.setSessionManager(defaultWebSessionManager()); //noSessionCreation
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

	/**
	 * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions),
	 * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 开启aop注解支持
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}