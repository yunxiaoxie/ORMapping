package com.crab.shiro.service;

import java.util.List;

import com.crab.shiro.domain.Module;
import com.crab.shiro.utils.PageResult;

public interface IModule {

	/**
	 * 添加模块信息，如果父模块的ID为0，则添加顶级模块
	 * @param module 模块信息
	 */
	public void addModule(Module module);
	
	/**
	 * 删除模块
	 * @param moduleId
	 * @param throw SystemException
	 */
	public void delModule(Integer moduleId);
	
	/**
	 * 更新模块信息
	 * @param module
	 * @param parentid
	 */
	public void updateModule(Module module);
	
	/**
	 * 查询特定的模块
	 * @param moduleId
	 * @return
	 */
	public Module findModule(Integer moduleId);
	
	/**
	 * 分页查询模块的信息
	 * @param parentId
	 * @return
	 */
	public PageResult searchModules(Integer parentId);
	/**
	 * 查询所有模块信息，不分页
	 * @return
	 */
	public List<?> searchAll(Integer parentId);
}
