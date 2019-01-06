package com.crab.service.impl;

import com.crab.domain.Role;
import com.crab.mapper.RoleMapper;
import com.crab.service.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleImpl implements IRole {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public void addRole(Role role) {
		if (null != role) {
			roleMapper.insert(role);
		}
	}

	@Override
	public void delRole(Integer roleId) {
		roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public void updateRole(Role role) {
		if (null != role && null != role.getId()) {
			roleMapper.updateByPrimaryKeySelective(role);
		}
	}

	@Override
	public Role findRole(Integer roleId) {
		if (null != roleId) {
			return roleMapper.selectByPrimaryKey(roleId);
		}
		return null;
	}

	@Override
	public List<?> searchRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> searchRolesOfUser(Integer userId) {
		List<Role> list = roleMapper.getRolesByUserId(userId);
		if (list == null || list.isEmpty()){
			return Collections.emptyList();
		}
		return list;
	}
	
	@Override
	public List<Integer> searchRolesOfUserDesc(Integer userId) {
		return roleMapper.getRoleIdsByUserIdDesc(userId);
	}
	
	@Override
	public List<Integer> searchRoleIdsOfUser(Integer userId) {
		return roleMapper.getRoleIdsByUserId(userId);
	}
}
