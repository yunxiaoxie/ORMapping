package com.crab.mybatis.service;

import java.util.List;

import org.mybatis.generator.paginator.Page;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.domain.UserInfo;

public interface UserService {

	/**
	 * findAll
	 * @return
	 */
	List<UserInfo> findAll(Page page);
	
	UserInfo findOne(int id);
	
	MyUser find(int id);
}
