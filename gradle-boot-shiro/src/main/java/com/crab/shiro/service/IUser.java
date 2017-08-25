package com.crab.shiro.service;

import java.util.List;

import com.crab.shiro.domain.User;
import com.crab.shiro.utils.PageResult;

public interface IUser {

	/**
	 * 添加用户信息
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 删除用户信息
	 * @param userId
	 */
	public void delUser(Integer userId);
	
	/**
	 * 查找特定的用户
	 * @param userId
	 * @return
	 */
	public User findUser(Integer userId);
	
	/**
	 * 查找特定的用户
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account);
	
	/**
	 * 查询用户拥有的所有的角色
	 * @param userId 用户ID
	 * @return UsersRoles对象的集合
	 */
	public List<?> searchUserRoles(Integer userId);
	
	/**
	 * 添加或更新用户拥有的角色，如果用户[userId]已经拥有角色[roleId]，
	 * 则更新其优先级[orderNo]，否则给用户分配相应的角色，并设置优先级
	 * @param userId
	 * @param roleId
	 * @param orderNo
	 */
	public void addOrUpdateUserRole(Integer userId,Integer roleId,Integer orderNo);
	
	/**
	 * 删除分配给用户的角色（关联）
	 * @param userId
	 * @param roleId
	 */
	public void delUserRole(Integer userId,Integer roleId);
	
	/**
	 * 用户更新自己的密码
	 * @param username
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(String username, String oldPwd, String newPwd);
	
	/**
	 * 查询所有用户信息
	 * @return
	 */
	public PageResult searchUsers();
	
	/**
	 * 更新用户角色中角色的优先级
	 * @param userId
	 * @param roleId
	 * @param orderNo
	 */
	public void updateUserRoleOrderNo(Integer userId, Integer roleId, Integer orderNo);
	
}
