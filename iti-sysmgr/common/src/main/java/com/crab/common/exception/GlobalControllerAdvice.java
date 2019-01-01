package com.crab.common.exception;


import com.crab.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller 增强器
 */
@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResult errorHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ApiResult.error(ex.getMessage());
    }

    /**
     * 拦截捕捉自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public ApiResult myErrorHandler(MyException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResult.error(ex.getMessage());
    }

}