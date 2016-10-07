package com.crab.mybatis.web;

import org.mybatis.generator.paginator.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.service.UserService;

@Controller
public class IndexController {
	@Autowired
	private UserService userService;

	/**
	 * 查询用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("finduser")
	public String findUser() {
		return JSON.toJSONString(userService.findAll(Page.newBuilder(2, 3, "/user/page")));
	}
	
	/**
	 * findById
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("user/{id}")
	public String findById(@PathVariable int id) {
		return JSON.toJSONString(userService.findOne(id));
	}
}
