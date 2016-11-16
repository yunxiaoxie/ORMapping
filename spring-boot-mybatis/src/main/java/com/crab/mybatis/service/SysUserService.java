package com.crab.mybatis.service;

import com.crab.mybatis.domain.SysUser;

public interface SysUserService {

	SysUser find(int id);
	
	/**
	 * 根据用户查询密码
	 * @param userName
	 * @param pwd
	 * @return
	 */
	SysUser findByPwd(String user, String pwd);
}
