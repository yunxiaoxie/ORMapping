package com.crab.mybatis.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.domain.SysUser;
import com.crab.mybatis.service.SysUserService;
import com.crab.mybatis.utils.Constant;
import com.json.Constants;
import com.json.JSONResult;

@Controller
@RequestMapping("/sys")
public class SysUserController extends BaseController{
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
	
	@ResponseBody
	@RequestMapping("loginsystem/{username}/{pwd}")
	public ModelMap login(@PathVariable String username, @PathVariable String pwd) {
		SysUser user = service.findByPwd(username, pwd);
		if (null != user) {
			Map<String, Object> result = new HashMap<>();
			result.put(Constant.SUCCESS_MSG, true);
			return retResult(Constant.SUCCESS_CODE, Constant.SUCCESS_MSG, result);
		}
		return null;
	}
}
