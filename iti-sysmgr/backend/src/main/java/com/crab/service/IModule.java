package com.crab.service;

import com.crab.domain.Module;
import com.crab.common.PageResult;

import java.util.List;

public interface IModule {

	void addModule(Module module);
	
	void delModule(Integer moduleId);
	
	void updateModule(Module module);
	
	Module findModule(Integer moduleId);
	
	/**
	 * 分页查询模块的信息
	 * @param parentId
	 * @return
	 */
	PageResult searchModules(Integer parentId);
	/**
	 * 查询所有模块信息，不分页
	 * @return
	 */
	List<?> searchAll(Integer parentId);
}
