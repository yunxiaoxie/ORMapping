package com.crab.mybatis.web;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.paginator3.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.service.MyUserService;
import com.crab.mybatis.utils.Constant;
import com.crab.mybatis.utils.LoggerUtil;
import com.crab.mybatis.utils.PageResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * POST /uri 创建 DELETE /uri/xxx 删除 PUT /uri/xxx 更新或创建 GET /uri/xxx 查看
 * 
 * @author YunxiaoXie
 */
@Api(value = "用户接口")
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
	@ApiOperation(value = "查询所有用户")
	@RequestMapping(value = "getAllUser", method = RequestMethod.GET)
	public ModelMap findAll() {
		try {
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, service.findAll());
		} catch(Exception e) {
			LoggerUtil.error("method findAll " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
	
	@ApiOperation(value = "查询所有分页用户")
	@RequestMapping(value = "getUserForPager", method = RequestMethod.GET) //consumes = "application/json"
	public ModelMap findForPager(@ApiParam(value = "分页对象", required = true) PageResult pageResult) { // remove @RequestBody for get request.
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

	@ApiOperation(value = "保存用户", notes="根据User对象创建用户")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public ModelMap saveUser(@ApiParam(value = "用户表单", required = true) @RequestBody MyUser user) {
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

	@ApiOperation(value = "更新用户", notes="根据User对象创建用户")
	@RequestMapping(value = "updateUser", method = RequestMethod.PUT)
	public ModelMap updateUser(@ApiParam(value = "用户表单", required = true) @RequestBody MyUser user) {
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
	
	@ApiOperation(value = "保存用户Selective", notes="根据User对象创建用户")
	@RequestMapping(value = "saveUserSelective", method = RequestMethod.POST)
	public ModelMap saveUserSelective(@ApiParam(value = "用户表单", required = true) @RequestBody MyUser user) {
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

	@ApiOperation(value = "更新用户Selective", notes="根据User对象创建用户")
	@RequestMapping(value = "updateUserSelective", method = RequestMethod.PUT)
	public ModelMap updateUserSelective(@ApiParam(value = "用户表单", required = true) @RequestBody MyUser user) {
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

	@ApiOperation(value = "删除用户")
	@RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
	public ModelMap deleteUser(@ApiParam(value = "用户Id", required = true) int id) {
		try {
			service.deleteById(id);
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, null);
		} catch(Exception e) {
			LoggerUtil.error("method deleteUser " + e.getMessage(), e);
			return retResult( Constant.FAIL_MSG,  e.getMessage(), null);
		}
	}
}
