package com.crab.mybatis.service.impl;

import java.util.List;

import org.mybatis.generator.paginator.Page;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.mybatis.generator.paginator3.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.mapper.MyUserMapper;
import com.crab.mybatis.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MyUserMapper mapper;

	@Override
	public List<MyUser> findAll(Page page) {
		return mapper.findAllPage(page);
	}

	@Override
	public MyUser findOne(int id) {
		return mapper.findOne(id);
	}

	@Override
	public List<MyUser> findAll() {
		return mapper.findAll();
	}

	@Override
	public List<MyUser> findAll(PageBounds page) {
		return mapper.findAllPage3(page);
	}

	@Override
	public List<MyUser> findAll(PageRowBounds page) {
		return mapper.findAllPage2(page);
	}
}
