package com.crab.mybatis.web;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.service.MyUserService;

/**
 * POST /uri 创建 DELETE /uri/xxx 删除 PUT /uri/xxx 更新或创建 GET /uri/xxx 查看
 * 
 * @author YunxiaoXie
 */
@Controller
public class MyUserController {
	@Autowired
	private MyUserService service;

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllUser", method = RequestMethod.GET)
	public String findAll() {
		return JSON.toJSONString(service.findAll());
	}

	@ResponseBody
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveUser(@RequestBody MyUser user) {
		if (null != user && StringUtils.isNotEmpty(user.getName())) {
			user.setCreateTime(new Date());
			return JSON.toJSONString(service.insert(user));
		}
		return "-1";
	}

	@ResponseBody
	@RequestMapping(value = "updateUser", method = RequestMethod.PUT)
	public String updateUser(@RequestBody MyUser user) {
		if (null != user && StringUtils.isNotEmpty(user.getName())) {
			return JSON.toJSONString(service.update(user));
		}
		return "-1";
	}
	
	@ResponseBody
	@RequestMapping(value = "saveUserSelective", method = RequestMethod.POST)
	public String saveUserSelective(@RequestBody MyUser user) {
		if (null != user && StringUtils.isNotEmpty(user.getName())) {
			user.setCreateTime(new Date());
			return JSON.toJSONString(service.insertSelective(user));
		}
		return "-1";
	}

	@ResponseBody
	@RequestMapping(value = "updateUserSelective", method = RequestMethod.PUT)
	public String updateUserSelective(@RequestBody MyUser user) {
		if (null != user && null != user.getId()) {
			user.setCreateTime(new Date());
			return JSON.toJSONString(service.updateBySelective(user));
		}
		return "-1";
	}

	@ResponseBody
	@RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
	public String deleteUser(int id) {
		return JSON.toJSONString(service.deleteById(id));
	}
}
