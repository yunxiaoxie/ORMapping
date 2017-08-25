package com.crab.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.shiro.domain.Permission;
import com.crab.shiro.mapper.PermissionMapper;
import com.crab.shiro.service.IPermission;

@Service
public class PermissionImpl implements IPermission {

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public void add(Permission permission) {
		permissionMapper.insert(permission);
	}

	@Override
	public void update(Permission permission) {
		permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public void del(Integer id) {
		permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Permission find(Integer id) {
		if (null != id) {
			return permissionMapper.selectByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public List<Permission> findAll() {
		return permissionMapper.getAll();
	}

}
