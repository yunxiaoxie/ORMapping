package com.crab.proxychain.test;

import com.crab.proxychain.Intercepte;

/**
 * 真实对象实现类
 * @author YunxiaoXie
 *
 */
public class TargetImpl implements Target {
	
	@Intercepte
	@Override
    public void execute() {
        System.out.println("Execute");
    }

	@Override
	public void execute2() {
		System.out.println("Execute2");
	}
}
