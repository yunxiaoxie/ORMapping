package com.crab.proxychain.test;

import com.crab.proxychain.Interceptor;

/**
 * 摸拟mybatis中拦截器，通过配置<plugins>标签，配置多个拦截器，对指定方法进行多次拦截。 
 */
public class Client {
	public static void main(String[] args) {
		// 真实对象，方法加拦截标注
		Target target = new TargetImpl();
		// 拦截一
		Interceptor interceptor = new InterceptorImpl();
		Target target1 = (Target)interceptor.register(target, target);
		// 拉截二
		Interceptor interceptor2 = new InterceptorImpl2();
		Target target2 = (Target)interceptor2.register(target, target1);
		// 两次拦截
		target2.execute();
		// 无拦截
		target2.execute2();
	}
}
