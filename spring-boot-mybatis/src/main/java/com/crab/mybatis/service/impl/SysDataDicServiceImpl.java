package com.crab.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.SysDataDic;
import com.crab.mybatis.mapper.SysDataDicMapper;
import com.crab.mybatis.service.SysDataDicService;

@Service
public class SysDataDicServiceImpl implements SysDataDicService {

	@Autowired
	private SysDataDicMapper mapper;

	@Override
	public SysDataDic find(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysDataDic> findByCode(Integer code) {
		return mapper.findByCode(code);
	}

}
