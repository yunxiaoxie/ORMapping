package com.crab.config;

import com.crab.common.utils.AjaxUtils;
import com.crab.domain.Module;
import com.crab.service.IAcl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public class RestAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Autowired
    private IAcl aclService;

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        String curUrl = getRequestUrl(request);
        // remove get parameter
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(curUrl) && curUrl.indexOf("?") != -1) {
            curUrl = curUrl.substring(0, curUrl.indexOf("?"));
        }
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal() == null
                || org.apache.commons.lang3.StringUtils.endsWithAny(curUrl, ".js",".css",".html")
                || org.apache.commons.lang3.StringUtils.endsWithAny(curUrl, ".jpg",".png",".gif", ".jpeg")
                || org.apache.commons.lang3.StringUtils.startsWith(curUrl, "/fonts")
                || Arrays.asList("/execption", "/unauthor", "/getModuleByUser", "/updatePermission", "/login", "/authorize", "/accessToken", "/user/info")
                .contains(curUrl)	) {
            return true;
        }
        List<Module> modules = aclService.searchModules(subject.getPrincipal().toString(), null);
        List<String> urls = new ArrayList<>();
        for (Module m : modules) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(m.getUrl())) {
                urls.add(m.getUrl());
            }
        }
        return urls.contains(curUrl);
    }

    /**
     * 获取当前URL+Parameter
     * @param request	拦截请求request
     * @return			返回完整URL
     */
    private String getRequestUrl(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest)request;
        String queryString = req.getQueryString();

        queryString = org.apache.commons.lang3.StringUtils.isBlank(queryString)?"": "?"+queryString;
        return req.getRequestURI()+queryString;
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
        // 如果未登录
        if (subject.getPrincipal() == null) {
            // AJAX 请求返回 JSON
            if (AjaxUtils.isAjaxRequest(WebUtils.toHttp(request))) {
                if (log.isDebugEnabled()) {
                    log.debug("用户: [{}] 请求 restful url : {}, 未登录被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));                }
                Map<String, Object> map = new HashMap<>();
                map.put("code", -1);
                AjaxUtils.writeJson(map, response);
            } else {
                // 其他请求跳转到登陆页面
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            // 如果已登陆, 但没有权限
            // 对于 AJAX 请求返回 JSON
            if (AjaxUtils.isAjaxRequest(WebUtils.toHttp(request))) {
                if (log.isDebugEnabled()) {
                    log.debug("用户: [{}] 请求 restful url : {}, 无权限被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));
                }

                Map<String, Object> map = new HashMap<>();
                map.put("code", -2);
                map.put("msg", "没有权限啊!");
                AjaxUtils.writeJson(map, response);
            } else {
                // 对于普通请求, 跳转到配置的 UnauthorizedUrl 页面.
                // 如果未设置 UnauthorizedUrl, 则返回 401 状态码
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }

        }
        return false;
    }
}
