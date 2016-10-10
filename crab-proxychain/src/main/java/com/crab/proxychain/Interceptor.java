package com.crab.proxychain;

/**
 * 1.把业务逻辑封装在此接口中，避免写死在TargetProxy中。
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
     * 因存在多次代理，所以记录真实对象，通过反射取方法标注确定是否需要拦截。
     * @param proxyTarget 代理对象
     * @param realTarget 真实对象
     * @return
     */
    public Object register(Object realTarget, Object proxyTarget);
    /**
     * 得到真实对象。
     * @return
     */
    public Object getRealObject();
}
