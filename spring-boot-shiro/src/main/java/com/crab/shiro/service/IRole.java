package com.crab.shiro.service;

import java.util.List;

import com.crab.shiro.domain.Role;

public interface IRole {

	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role);
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void delRole(Integer roleId);
	
	public void updateRole(Role role);
	
	public Role findRole(Integer roleId);

	/**
	 * 分页查询角色的信息
	 * @return
	 */
	public List<?> searchRoles();
	
	/**
	 * 查找用户的所有角色
	 * 存在多角色时,按优先级从小到大排列,优先级越小越大.
	 * @param userId
	 * @return
	 */
	public List<Integer> searchRoleIdsOfUser(Integer userId);
	
	/**
	 * 查找用户的所有角色
	 * 存在多角色时,按优先级从小到大排列,优先级越小越大.
	 * @param userId
	 * @return
	 */
	public List<Role> searchRolesOfUser(Integer userId);
	
	/**
	 * 查找用户的所有角色
	 * 存在多角色时,按优先级从小到大排列,优先级越小越大.
	 * @param userId
	 * @return
	 */
	public List<Integer> searchRolesOfUserDesc(Integer userId);
}
