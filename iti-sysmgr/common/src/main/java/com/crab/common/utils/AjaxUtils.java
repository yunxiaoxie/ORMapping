package com.crab.common.utils;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AjaxUtils {

    /**
     * 是否是Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null && "XMLHttpRequest".equalsIgnoreCase(requestedWith);
    }

    /**
     * 输出JSON
     */
    public static void writeJson(Map<String, Object> map, ServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtils.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 输出JSON
     */
    public static void writeJson(Map<String, Object> map, ServletRequest req, ServletResponse resp) {
        PrintWriter out = null;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        try {
            //跨域的header设置
            response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", request.getMethod());
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            //防止乱码，适用于传输JSON数据
            //response.setHeader("Content-Type","application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtils.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}