package com.crab.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.SysUser;
import com.crab.mybatis.mapper.SysUserMapper;

@Service
public class SysUserService {

	@Autowired
	private SysUserMapper mapper;

	public SysUser find(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	public SysUser findByPwd(String user, String pwd) {
		return mapper.getUserByPwd(user, pwd);
	}

}
