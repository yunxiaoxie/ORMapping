package com.crab.proxychain;

/**
 * 把业务逻辑封装在此接口中，避免写死在TargetProxy中。
 * @author YunxiaoXie
 *
 */
public interface Interceptor {
	/**
	 * 业务拦截。
	 * @return 
	 */
    public Object intercept(Invocation invocation) throws Throwable;
    
    /**
     * @param target 目标对象(可能为真实对象或代理对象)
     * @return
     */
    public Object register(Object target);
}
