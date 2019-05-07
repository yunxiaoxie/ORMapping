package com.crab.common.shiro.filter;

import com.crab.common.ApiResult;
import com.crab.common.utils.AjaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 修改后的 perms 过滤器, 添加对 AJAX 请求的支持.
 */
@Slf4j
public class RestAuthorizationFilter extends AuthorizationFilter {

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request,response);
        String[] perms = (String[])mappedValue;
        if(perms == null || perms.length ==0){
            return true;
        }
        for(String perm:perms){
            if(subject.isPermitted(perm)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = this.getPathWithinApplication(request);

        String[] strings = path.split("==");

        if (strings.length <= 1) {
            // 普通的 URL, 正常处理
            return this.pathsMatch(strings[0], requestURI);
        } else {
            // 获取当前请求的 http method.
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();

            // 匹配当前请求的 http method 与 过滤器链中的的是否一致
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestURI);
        }
    }

    /**
     * 当没有权限被拦截时:
     *          如果是 AJAX 请求, 则返回 JSON 数据.
     *          如果是普通请求, 则跳转到配置 UnauthorizedUrl 页面.
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // for ajax response.
        if (log.isDebugEnabled()) {
            log.debug("用户: [{}] 请求 restful url : {}, 无权限被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));
        }
        AjaxUtils.writeJson(ApiResult.error("no permission"), response);
        return false;
    }
}
