package com.crab.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.SysUser;
import com.crab.mybatis.mapper.SysUserMapper;
import com.crab.mybatis.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserMapper mapper;

	@Override
	public SysUser find(int id) {
		return mapper.selectByPrimaryKey(id);
	}

}
