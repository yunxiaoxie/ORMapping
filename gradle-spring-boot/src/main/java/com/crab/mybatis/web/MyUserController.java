package com.crab.mybatis.web;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.paginator3.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.service.MyUserService;
import com.crab.mybatis.utils.Constant;
import com.crab.mybatis.utils.LoggerUtil;
import com.crab.mybatis.utils.PageResult;

/**
 * POST /uri 创建 DELETE /uri/xxx 删除 PUT /uri/xxx 更新或创建 GET /uri/xxx 查看
 * 
 * @author YunxiaoXie
 */
@RestController
@RequestMapping("/user/")
public class MyUserController extends BaseController {
	@Autowired
	private MyUserService service;

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("getAllUser")
	public ModelMap findAll() {
		try {
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, service.findAll());
		} catch(Exception e) {
			LoggerUtil.error("method findAll " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	@GetMapping("getUserForPager") //consumes = "application/json"
	public ModelMap findForPager(PageResult pageResult) { // remove @RequestBody for get request.
		try {
			if (null != pageResult) {
				PageResult pr = new PageResult();
				PageBounds pb = new PageBounds(pageResult.getPageNo(), pageResult.getPageSize());
				pr.setPageNo(pageResult.getPageNo());
				pr.setPageSize(pageResult.getPageSize());
				pr.setData(service.findForPager(pb));
				pr.setTotalRecord(pb.getTotalRecord());
				pr.calcPages();
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, pr);
			}
			return null;
		} catch(Exception e) {
			LoggerUtil.error("method findForPager " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}

	@PostMapping("saveUser")
	public ModelMap saveUser(@RequestBody MyUser user) {
		try {
			if (null != user && StringUtils.isNotEmpty(user.getName())) {
				user.setCreateTime(new Date());
				service.insert(user);
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
			}
			return retResult(Constant.HTTP_200, Constant.FAIL_MSG, null);
		} catch(Exception e) {
			LoggerUtil.error("method saveUser " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.PUT)
	public ModelMap updateUser(@RequestBody MyUser user) {
		try {
			if (null != user && StringUtils.isNotEmpty(user.getName())) {
				service.update(user);
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
			}
			return retResult(Constant.HTTP_200, Constant.FAIL_MSG, null);
		} catch(Exception e) {
			LoggerUtil.error("method updateUser " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	@PostMapping("saveUserSelective")
	public ModelMap saveUserSelective(@RequestBody MyUser user) {
		try {
			if (null != user && StringUtils.isNotEmpty(user.getName())) {
				user.setCreateTime(new Date());
				service.insertSelective(user);
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
			}
			return retResult(Constant.HTTP_200, Constant.FAIL_MSG, null);
		} catch(Exception e) {
			LoggerUtil.error("method saveUserSelective " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}

	@RequestMapping(value = "updateUserSelective", method = RequestMethod.PUT)
	public ModelMap updateUserSelective(@RequestBody MyUser user) {
		try {
			if (null != user && null != user.getId()) {
				user.setCreateTime(new Date());
				service.updateBySelective(user);
				return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
			}
			return retResult(Constant.HTTP_200, Constant.FAIL_MSG, null);
		} catch(Exception e) {
			LoggerUtil.error("method updateUserSelective " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}

	@DeleteMapping("deleteUser")
	public ModelMap deleteUser(int id) {
		try {
			service.deleteById(id);
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
		} catch(Exception e) {
			LoggerUtil.error("method deleteUser " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
}
