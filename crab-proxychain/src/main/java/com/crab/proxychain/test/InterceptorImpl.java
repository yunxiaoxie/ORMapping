package com.crab.proxychain.test;

import com.crab.proxychain.Intercept;
import com.crab.proxychain.Interceptor;
import com.crab.proxychain.Invocation;
import com.crab.proxychain.Plugin;

@Intercept("execute")
public class InterceptorImpl implements Interceptor{
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("Go to interceptor");
		return invocation.proceed();
	}

	@Override
	public Object register(Object target) {
		return Plugin.bind(target, this);
	}

}
