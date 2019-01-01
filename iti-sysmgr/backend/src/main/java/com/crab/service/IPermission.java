package com.crab.service;


import com.crab.domain.Permission;

import java.util.List;

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
	void add(Permission permission);
	
	void update(Permission permission);
	
	void del(Integer id);
	
	Permission find(Integer id);
	
	List<Permission> findAll();
}
