package com.crab.common.shiro.filter;

import com.crab.common.ApiResult;
import com.crab.common.utils.AjaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class RestRoleFilter extends RestAuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request,response);
        String[] roles = (String[])mappedValue;
        if(roles == null || roles.length ==0){
            return true;
        }
        for(String role:roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // for ajax response.
        if (log.isDebugEnabled()) {
            log.debug("用户: [{}] 请求 restful url : {}, 无权限被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));
        }
        AjaxUtils.writeJson(ApiResult.error("no role"), response);
        return false;
    }
}
