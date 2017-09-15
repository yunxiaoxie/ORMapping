package com.crab.mybatis.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crab.mybatis.domain.SysUser;
import com.crab.mybatis.service.SysUserService;
import com.crab.mybatis.utils.Constant;
import com.crab.mybatis.utils.LoggerUtil;

@RestController
@RequestMapping("/sys")
public class SysUserController extends BaseController{
	
	@Autowired
	private SysUserService service;

	/**
	 * findById
	 * @param id
	 * @return
	 */
	@RequestMapping("Login/{id}")
	public ModelMap findById(@PathVariable int id) {
		try {
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, service.find(id));
		} catch(Exception e) {
			LoggerUtil.error("method findById " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	@RequestMapping("Login/{username}/{pwd}")
	public ModelMap findByPwd(@PathVariable String username, @PathVariable String pwd) {
		try {
			SysUser user = service.findByPwd(username, pwd);
			if (user != null) {
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
			} else {
				return retResult(Constant.HTTP_200, Constant.FAIL_MSG, null);
			}
		} catch(Exception e) {
			LoggerUtil.error("method findByPwd " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	@RequestMapping("loginsystem/{username}/{pwd}")
	public ModelMap login(@PathVariable String username, @PathVariable String pwd) {
		try {
			SysUser user = service.findByPwd(username, pwd);
			if (null != user) {
				Map<String, Object> result = new HashMap<>();
				result.put(Constant.SUCCESS_MSG, true);
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, result);
			}
			return null;
		} catch(Exception e) {
			LoggerUtil.error("method login " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	@RequestMapping("loginsystem2")
	public ModelMap login2(@RequestBody Map<String, String> paramMap) {
		try {
			String username = paramMap.get("username");
			String pwd = paramMap.get("pwd");
			SysUser user = service.findByPwd(username, pwd);
			if (null != user) {
				Map<String, Object> result = new HashMap<>();
				result.put(Constant.SUCCESS_MSG, true);
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, result);
			}
			return null;
		} catch(Exception e) {
			LoggerUtil.error("method login " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
}
