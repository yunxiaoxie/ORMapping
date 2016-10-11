package com.crab.proxychain.test;

import com.crab.proxychain.Intercept;

/**
 * 真实对象实现类
 * @author YunxiaoXie
 *
 */
public class TargetImpl implements Target {
	
	@Intercept
	@Override
    public void execute() {
        System.out.println("Execute");
    }

	@Override
	public void execute2() {
		System.out.println("Execute2");
	}
}
