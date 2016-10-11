package com.crab.proxychain;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据拦截器生成一个N级代理对象。
 * @author YunxiaoXie
 */
public class PluginFactory<T> {

	private PluginFactory() {
		
	}
	/**
	 * 简化创建代理对象，方便其它地方调用。
	 * @param clazz 真实对象
	 * @param list 拦截列表
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<? extends T> clazz, List<Class<? extends Interceptor>> list)  throws Throwable{
		// get targetImpl object
		Class<?> targetClazz = Class.forName(clazz.getName());
		T targetImpl = (T)targetClazz.newInstance();
		// get interceptor object list
		List<Interceptor> intercepts = new ArrayList<Interceptor> ();
		for (Class<? extends Interceptor> inter : list) {
			Class<?> interClazz = Class.forName(inter.getName());
			intercepts.add((Interceptor)interClazz.newInstance());
		}
		// proxy object via interceptors
		for (Interceptor inter : intercepts) {
			targetImpl = (T) inter.register(targetImpl);
		}
		return targetImpl;
	}
}
