package com.crab.common.shiro.filter;

import com.crab.common.utils.AjaxUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class RestLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        Subject subject = getSubject(request, response);
        try {
            subject.logout();
        } catch (SessionException ise) {
            ise.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("messages", "logout success.");
        AjaxUtils.writeJson(map, request, response);

        // stop filter chain.
        return false;
    }

}
