package com.crab.shiro.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", req.getRequestURL());
		mav.addObject("stack", StringUtils.join(e.getStackTrace(), "\r\t"));
		mav.addObject("message", e.getMessage());
		mav.setViewName("error.jsp");
		return mav;
	}
	
	/*@ExceptionHandler(value = SystemException.class)
    @ResponseBody
    public ErrorInfo<StackTraceElement[]> sysErrorHandler(HttpServletRequest req, SystemException e) throws Exception {
        ErrorInfo<StackTraceElement[]> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData(e.getStackTrace());
        r.setUrl(req.getRequestURL().toString());
        return r;
    }*/
}
