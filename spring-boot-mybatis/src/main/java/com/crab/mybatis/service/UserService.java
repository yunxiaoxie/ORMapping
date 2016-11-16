package com.crab.mybatis.service;

import java.util.List;

import org.mybatis.generator.paginator.Page;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.mybatis.generator.paginator3.PageBounds;

import com.crab.mybatis.domain.MyUser;

public interface UserService {

	/**
	 * findAll
	 * 
	 * @return
	 */
	List<MyUser> findAll();

	List<MyUser> findAll(Page page);
	
	List<MyUser> findAll(PageBounds page);
	
	List<MyUser> findAll(PageRowBounds page);

	MyUser findOne(int id);

}
