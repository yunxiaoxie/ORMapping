package com.crab.mybatis.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	/*@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", req.getRequestURL());
		mav.addObject("stack", StringUtils.join(e.getStackTrace(), "\r\t"));
		mav.addObject("message", e.getMessage());
		mav.setViewName("error.jsp");
		return mav;
	}*/
	
	@ExceptionHandler(value = SystemException.class)
    @ResponseBody
    public ErrorInfo<String> sysErrorHandler(HttpServletRequest req, SystemException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData(StringUtils.join(e.getStackTrace(), "\r\t"));
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}
