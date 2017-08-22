package com.crab.mybatis.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.paginator.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.exception.SystemException;
import com.crab.mybatis.service.UserService;
import com.crab.mybatis.utils.Constant;
import com.crab.mybatis.utils.LoggerUtil;

@RestController
@RequestMapping("/")
public class IndexController extends BaseController{
	@Autowired
	private UserService userService;

	/**
	 * 查询用户
	 * @return
	 */
	@RequestMapping("finduser")
	public ModelMap findUser() {
		try {
			List<MyUser> list = userService.findAll(new Page(2, 3));
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, list);
		} catch(Exception e) {
			LoggerUtil.error("method findUser " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	/**
	 * findById
	 * @param id
	 * @return
	 */
	@RequestMapping("user/{id}")
	public ModelMap findById(@PathVariable Integer id) throws Exception{
		MyUser user = userService.findOne(id);
		return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, user);
	}
	
	@RequestMapping("execption")
	public void execption(String id) throws SystemException {
		if (StringUtils.isEmpty(id)) {
			throw new SystemException("发生系统错误");
		}
	}
}
