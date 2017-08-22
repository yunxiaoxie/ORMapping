package com.crab.mybatis.service;

import java.util.List;

import org.mybatis.generator.paginator3.PageBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.mapper.MyUserMapper;

@Service
public class MyUserService {

	private Logger logger = LoggerFactory.getLogger(MyUserService.class);

	@Autowired
	private MyUserMapper mapper;

	public List<MyUser> findAll() {
		return mapper.findAll();
	}

	public List<MyUser> findForPager(PageBounds pageRow) {
		return mapper.findAllPage3(pageRow);
	}

	public int insert(MyUser user) {
		logger.info("Invoked insert method:" + user);
		int result = mapper.insert(user);
		return result;
	}

	public int deleteById(Integer id) {
		logger.info("Invoked delete method:" + id);
		return mapper.deleteByPrimaryKey(id);
	}

	public int update(MyUser user) {
		logger.info("Invoked update method:" + user);
		return mapper.updateByPrimaryKey(user);
	}

	public int insertSelective(MyUser record) {
		return mapper.insertSelective(record);
	}

	public int updateBySelective(MyUser record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	public MyUser findOne(int id) {
		return mapper.findOne(id);
	}

}
