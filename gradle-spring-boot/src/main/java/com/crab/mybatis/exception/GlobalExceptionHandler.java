package com.crab.mybatis.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crab.mybatis.utils.Constant;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	public ModelMap retResult(String code, String msg, Object content) {
		ModelMap modelMap = new ModelMap();
		modelMap.put("code", code);
		modelMap.put("msg", msg);
		modelMap.put("result", content);
		return modelMap;
	}
	
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ModelMap errorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (e instanceof SystemException) {
			return retResult(Constant.FAIL_MSG, e.getMessage(), e.getStackTrace());
		}
		return retResult(Constant.FAIL_MSG, e.getMessage(), null);
    }
	
}
