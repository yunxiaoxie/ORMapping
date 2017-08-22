package com.crab.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.mybatis.domain.SysDataDic;
import com.crab.mybatis.mapper.SysDataDicMapper;

@Service
public class SysDataDicService {

	@Autowired
	private SysDataDicMapper mapper;

	public SysDataDic find(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	public List<SysDataDic> findByCode(Integer code) {
		return mapper.findByCode(code);
	}

}
