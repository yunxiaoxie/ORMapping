package com.crab.proxychain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 真实对象代理对象
 * 原计划两种拦截，一是真实对象方法上标注，二是拦截器上标注(指定方法)
 * 拦截一需要记录真实对象以获取方法标注，比较麻烦。
 * @author YunxiaoXie
 *
 */
public class Plugin implements InvocationHandler {
	/**
	 * 拦截对象。
	 */
	private Object target;
	/**
	 * 拦截器。
	 */
	private Interceptor interceptor;

	private Plugin(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}

	/**
	 * 生成一个目标对象的代理对象。
	 * target可为目标对象或代理对象。
	 */
	public static Object bind(Object target, Interceptor interceptor) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new Plugin(target, interceptor));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//拦截器上标注(指定方法)
		Intercept intercept = interceptor.getClass().getAnnotation(Intercept.class);
//		Method m = getMethod(method.getName(), interceptor.getRealObject().getClass());
		if (intercept != null && method.getName().equals(intercept.value())) {
			// 把客户端的拦截逻辑委托给第三方
			return interceptor.intercept(new Invocation(target, method, args));
		}
		return method.invoke(target, args);
	}
	
}