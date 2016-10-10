package com.crab.proxychain.test;

import com.crab.proxychain.Interceptor;
import com.crab.proxychain.Invocation;
import com.crab.proxychain.Plugin;

public class InterceptorImpl implements Interceptor{
	
	private Object realTarget;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("Go to interceptor");
		return invocation.proceed();
	}

	@Override
	public Object register(Object realTarget, Object proxyTarget) {
		this.realTarget = realTarget;
		return Plugin.bind(proxyTarget, this);
	}

	@Override
	public Object getRealObject() {
		return this.realTarget;
	}

}
