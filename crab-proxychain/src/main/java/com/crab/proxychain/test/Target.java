package com.crab.proxychain.test;

/**
 * 真实对象
 * @author YunxiaoXie
 *
 */
public interface Target {
	/**
	 * 真实对象方法。
	 */
	public void execute();
	
	/**
	 * 其它方法。
	 */
	public void execute2();
}
