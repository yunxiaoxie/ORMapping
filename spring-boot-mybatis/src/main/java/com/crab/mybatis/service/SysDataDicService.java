package com.crab.mybatis.service;

import java.util.List;

import com.crab.mybatis.domain.SysDataDic;

/**
 * System Dictionary.
 * @author YunxiaoXie
 *
 */
public interface SysDataDicService {

	SysDataDic find(int id);
	
	/**
	 * 根据用户查询密码
	 * @param userName
	 * @param pwd
	 * @return
	 */
	List<SysDataDic> findByCode(Integer code);
}
