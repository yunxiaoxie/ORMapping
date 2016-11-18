package com.crab.mybatis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.domain.SysUser;
import com.crab.mybatis.service.SysUserService;
import com.json.Constants;
import com.json.JSONResult;

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
	@RequestMapping("Login/{id}")
	public String findById(@PathVariable int id) {
		return JSON.toJSONString(service.find(id));
	}
	
	@ResponseBody
	@RequestMapping("Login/{username}/{pwd}")
	public String findByPwd(@PathVariable String username, @PathVariable String pwd) {
		SysUser user = service.findByPwd(username, pwd);
		if (null != user) {
			return JSON.toJSONString(new JSONResult(Constants.SUCCESS));
		}
		return null;
	}
}
