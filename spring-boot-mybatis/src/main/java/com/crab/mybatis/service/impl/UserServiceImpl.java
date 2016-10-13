package com.crab.mybatis.service.impl;

import java.util.List;

import org.mybatis.generator.paginator.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.domain.UserInfo;
import com.crab.mybatis.mapper.MyUserMapper;
import com.crab.mybatis.mapper.UserMapper;
import com.crab.mybatis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper UserMapper;
	
	@Autowired
	private MyUserMapper MyUserMapper;

	@Override
	public List<UserInfo> findAll(Page page) {
		return UserMapper.findAll(page);
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
