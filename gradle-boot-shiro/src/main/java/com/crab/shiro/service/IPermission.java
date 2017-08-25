package com.crab.shiro.service;

import java.util.List;

import com.crab.shiro.domain.Permission;

/**
 * 权限列表
 * @author YunxiaoXie
 *
 */
public interface IPermission {

	/**
	 * 添加权限
	 * @param permission
	 */
	public void add(Permission permission);
	
	public void update(Permission permission);
	
	public void del(Integer id);
	
	public Permission find(Integer id);
	
	public List<Permission> findAll();
}
