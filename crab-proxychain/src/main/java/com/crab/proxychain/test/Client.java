package com.crab.proxychain.test;

import java.util.Arrays;

import com.crab.proxychain.PluginFactory;

/**
 * 摸拟mybatis中拦截器，通过配置<plugins>标签，配置多个拦截器，对指定方法进行多次拦截。 
 */
public class Client {
	public static void main(String[] args) throws Throwable {
//		// 真实对象，方法加拦截标注
//		Target target = new TargetImpl();
//		// 拦截一
//		Interceptor interceptor = new InterceptorImpl();
//		target = (Target)interceptor.register(target);
//		// 拉截二
//		Interceptor interceptor2 = new InterceptorImpl2();
//		target = (Target)interceptor2.register(target);
//		// 两次拦截
//		target.execute();
//		// 无拦截
//		target.execute2();
		
		Target target = PluginFactory.getProxy(TargetImpl.class, Arrays.asList(InterceptorImpl.class, InterceptorImpl2.class));
		target.execute();
		target.execute2();
	}
}
