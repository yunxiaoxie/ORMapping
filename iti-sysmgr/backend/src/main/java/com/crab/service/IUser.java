package com.crab.service;

import com.crab.domain.User;
import com.crab.common.PageResult;

import java.util.List;

public interface IUser {

	void addUser(User user);
	
	void updateUser(User user);

	void delUser(Integer userId);
	
	User findUser(Integer userId);
	
	User findUser(String account, String pwd);

	User findUserByAccount(String account);
	
	/**
	 * 查询用户拥有的所有的角色
	 * @param userId 用户ID
	 * @return UsersRoles对象的集合
	 */
	List<?> searchUserRoles(Integer userId);
	
	/**
	 * 添加或更新用户拥有的角色，如果用户[userId]已经拥有角色[roleId]，
	 * 则更新其优先级[orderNo]，否则给用户分配相应的角色，并设置优先级
	 * @param userId
	 * @param roleId
	 * @param orderNo
	 */
	void addOrUpdateUserRole(Integer userId, Integer roleId, Integer orderNo);
	
	/**
	 * 删除分配给用户的角色（关联）
	 * @param userId
	 * @param roleId
	 */
	void delUserRole(Integer userId, Integer roleId);
	
	/**
	 * 用户更新自己的密码
	 * @param username
	 * @param oldPwd
	 * @param newPwd
	 */
	void updatePwd(String username, String oldPwd, String newPwd);
	
	/**
	 * 查询所有用户信息
	 * @return
	 */
	PageResult searchUsers();
	
	/**
	 * 更新用户角色中角色的优先级
	 * @param userId
	 * @param roleId
	 * @param orderNo
	 */
	void updateUserRoleOrderNo(Integer userId, Integer roleId, Integer orderNo);
	
}
