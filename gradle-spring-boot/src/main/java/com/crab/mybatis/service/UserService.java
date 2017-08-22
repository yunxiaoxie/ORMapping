package com.crab.mybatis.service;

import java.util.List;

import org.mybatis.generator.paginator.Page;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.mybatis.generator.paginator3.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.mapper.MyUserMapper;

@Service
public class UserService {

	@Autowired
	private MyUserMapper mapper;

	public List<MyUser> findAll(Page page) {
		return mapper.findAllPage(page);
	}

	public MyUser findOne(int id) {
		return mapper.findOne(id);
	}

	public List<MyUser> findAll() {
		return mapper.findAll();
	}

	public List<MyUser> findAll(PageBounds page) {
		return mapper.findAllPage3(page);
	}

	public List<MyUser> findAll(PageRowBounds page) {
		return mapper.findAllPage2(page);
	}
}
