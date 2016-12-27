package com.crab.mybatis.service;

import java.util.List;

import org.mybatis.generator.paginator3.PageBounds;

import com.crab.mybatis.domain.MyUser;

public interface MyUserService {

	MyUser findOne(int id);
	
	List<MyUser> findAll();
	
	/**
	 * 分页查询列表数据.
	 * @param userName
	 * @param pwd
	 * @return
	 */
	List<MyUser> findForPager(PageBounds pageRow);
	
	int insert(MyUser user);

	int deleteById(Integer id);

	int update(MyUser user);
	
	int insertSelective(MyUser record);
	
	int updateBySelective(MyUser record);
}
