package com.crab.shiro.web;

import org.springframework.ui.ModelMap;

/**
 * Base controller.
 * 
 * @author YunxiaoXie
 *
 */
public class BaseControl {

	/**
	 * 返回前台通用接口规范
	 */
	public ModelMap retResult(String code, String msg, Object content) {
		ModelMap modelMap = new ModelMap();
		modelMap.put("code", code);
		modelMap.put("msg", msg);
		modelMap.put("result", content);
		return modelMap;
	}

}
