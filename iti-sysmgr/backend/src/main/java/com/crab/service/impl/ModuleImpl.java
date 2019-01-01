package com.crab.service.impl;

import com.crab.domain.Module;
import com.crab.mapper.ModuleMapper;
import com.crab.common.PageResult;
import com.crab.service.IModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
