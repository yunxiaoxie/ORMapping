package com.crab.redis.service;

import java.util.Map;

import com.crab.redis.domain.SysDataDic;

public interface ISysDataDicService {

	/**
	 * findAll
	 * @return
	 */
	Map<String, String> findAll();
	
	SysDataDic findOne(int id);
	
}
