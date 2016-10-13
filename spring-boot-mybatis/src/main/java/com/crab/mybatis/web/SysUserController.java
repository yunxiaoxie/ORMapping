package com.crab.mybatis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.service.SysUserService;

@Controller
public class SysUserController {
	@Autowired
	private SysUserService service;

	/**
	 * findById
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("sysuser/{id}")
	public String findById(@PathVariable int id) {
		return JSON.toJSONString(service.find(id));
	}
}
