package com.crab.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.shiro.domain.Module;
import com.crab.shiro.mapper.ModuleMapper;
import com.crab.shiro.service.IModule;
import com.crab.shiro.utils.PageResult;

@Service
public class ModuleImpl implements IModule {

	@Autowired
	private ModuleMapper moduleMapper;

	@Override
	public void addModule(Module module) {
		if (null != module) {
			moduleMapper.insert(module);
		}
	}

	@Override
	public void delModule(Integer moduleId) {
		if (null != moduleId) {
			moduleMapper.deleteByPrimaryKey(moduleId);
		}
	}

	@Override
	public void updateModule(Module module) {
		if (null != module && module.getId() != null) {
			moduleMapper.updateByPrimaryKeySelective(module);
		}
	}

	@Override
	public Module findModule(Integer moduleId) {
		if (null != moduleId) {
			return moduleMapper.selectByPrimaryKey(moduleId);
		}
		return null;
	}

	@Override
	public PageResult searchModules(Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> searchAll(Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
