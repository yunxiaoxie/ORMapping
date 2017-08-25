package com.crab.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.shiro.domain.User;
import com.crab.shiro.mapper.UserMapper;
import com.crab.shiro.service.IUser;
import com.crab.shiro.utils.PageResult;

@Service
public class UserImpl implements IUser {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void addUser(User user) {
		if (null != user) {
			userMapper.insert(user);
		}
	}

	@Override
	public void updateUser(User user) {
		if (null != user && null != user.getId()) {
			userMapper.updateByPrimaryKeySelective(user);
		}
	}

	@Override
	public void delUser(Integer userId) {
		if (null != userId) {
			userMapper.deleteByPrimaryKey(userId);
		}
	}

	@Override
	public User findUser(Integer userId) {
		if (null != userId) {
			return userMapper.selectByPrimaryKey(userId);
		}
		return null;
	}

	@Override
	public List<?> searchUserRoles(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrUpdateUserRole(Integer userId, Integer roleId, Integer orderNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delUserRole(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePwd(String username, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub

	}

	@Override
	public PageResult searchUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserRoleOrderNo(Integer userId, Integer roleId, Integer orderNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findUserByAccount(String account) {
		return userMapper.findByAccount(account);
	}

}
