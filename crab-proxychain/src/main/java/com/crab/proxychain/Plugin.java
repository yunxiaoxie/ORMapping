package com.crab.proxychain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 真实对象代理对象
 * 
 * @author YunxiaoXie
 *
 */
public class Plugin implements InvocationHandler {
	/**
	 * 真实对象。
	 */
	private Object target;
	/**
	 * 拦截逻辑。
	 */
	private Interceptor interceptor;

	private Plugin(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}

	/**
	 * 生成一个目标对象的代理对象
	 */
	public static Object bind(Object target, Interceptor interceptor) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new Plugin(target, interceptor));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Method m = getMethod(method.getName(), interceptor.getRealObject().getClass());
		// 若拦截方法名与当前方法名相同则拦截
		if (m != null && m.getAnnotation(Intercepte.class) != null) {
			// 执行客户端定义的拦截逻辑
			return interceptor.intercept(new Invocation(target, method, args));
		}
		return method.invoke(target, args);
	}
	
	/**
	 * Get method by name from class.
	 * @param name
	 * @param clazz
	 * @return
	 */
	private Method getMethod(String name, Class<?> clazz) {
		if (name != null && !name.isEmpty()) {
			Method[] methods = clazz.getMethods();
			for (Method m : methods) {
				if (name.equals(m.getName())) {
					return m;
				}
			}
		}
		return null;
	}
}