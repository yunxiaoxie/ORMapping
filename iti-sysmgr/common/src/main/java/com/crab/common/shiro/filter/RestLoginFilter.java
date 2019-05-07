package com.crab.common.shiro.filter;

import com.crab.common.ApiResult;
import com.crab.common.jwt.JwtHelper;
import com.crab.common.utils.AjaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class RestLoginFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        Subject subject = getSubject(servletRequest, servletResponse);

        if (subject.isAuthenticated()) {
            return true;
        } else {
            AuthenticationToken token = createToken(servletRequest);
            try {
                subject.login(token);
                return true;
            } catch (AuthenticationException e) {
                AjaxUtils.writeJson(ApiResult.error("please have a login."), servletResponse);
                return false;
            }
        }
    }

    /**
     *生成token
     */
    private AuthenticationToken createToken(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String account = httpRequest.getParameter("account");
        String password = httpRequest.getParameter("password");
        UsernamePasswordToken uptoken = new UsernamePasswordToken(account,password);
        return uptoken;
    }

}
