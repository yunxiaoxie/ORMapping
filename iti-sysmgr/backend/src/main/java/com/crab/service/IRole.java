package com.crab.service;

import com.crab.domain.Role;

import java.util.List;

public interface IRole {

	void addRole(Role role);
	
	void delRole(Integer roleId);
	
	void updateRole(Role role);
	
	Role findRole(Integer roleId);

	/**
	 * 分页查询角色的信息
	 * @return
	 */
	List<?> searchRoles();
	
	/**
	 * 查找用户的所有角色
	 * 存在多角色时,按优先级从小到大排列,优先级越小越大.
	 * @param userId
	 * @return
	 */
	List<Integer> searchRoleIdsOfUser(Integer userId);
	
	/**
	 * 查找用户的所有角色
	 * 存在多角色时,按优先级从小到大排列,优先级越小越大.
	 * @param userId
	 * @return
	 */
	List<Role> searchRolesOfUser(Integer userId);
	
	/**
	 * 查找用户的所有角色
	 * 存在多角色时,按优先级从小到大排列,优先级越小越大.
	 * @param userId
	 * @return
	 */
	List<Integer> searchRolesOfUserDesc(Integer userId);
}
