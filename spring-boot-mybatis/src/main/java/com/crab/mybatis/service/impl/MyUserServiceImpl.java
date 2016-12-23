package com.crab.mybatis.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.mapper.MyUserMapper;
import com.crab.mybatis.service.MyUserService;

@Service
public class MyUserServiceImpl implements MyUserService {

	private Log logger = LogFactory.getLog(MyUserService.class);

	@Autowired
	private MyUserMapper mapper;

	@Override
	public List<MyUser> findAll() {
		return mapper.findAll();
	}

	@Override
	public List<MyUser> findForPager(PageRowBounds pageRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(MyUser user) {
		logger.info("Invoked insert method:" + user);
		int result = mapper.insert(user);
		return result;
	}

	@Override
	public int deleteById(Integer id) {
		logger.info("Invoked delete method:" + id);
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(MyUser user) {
		logger.info("Invoked update method:" + user);
		return mapper.updateByPrimaryKey(user);
	}

	@Override
	public int insertSelective(MyUser record) {
		return mapper.insertSelective(record);
	}

	@Override
	public int updateBySelective(MyUser record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public MyUser findOne(int id) {
		return mapper.findOne(id);
	}

}
