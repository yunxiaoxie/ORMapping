package com.crab.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.domain.UserInfo;
import com.crab.mybatis.mapper.MyUserMapper;
import com.crab.mybatis.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper UserMapper;
	
	@Autowired
	private MyUserMapper MyUserMapper;

	@Override
	public List<UserInfo> findAll() {
		return UserMapper.findAll();
	}

	@Override
	public UserInfo findOne(int id) {
		return UserMapper.findOne(id);
	}

	@Override
	public MyUser find(int id) {
		return MyUserMapper.selectByPrimaryKey(id);
	}
}
