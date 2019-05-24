package com.crab.common.shiro.filter;

import com.crab.common.ApiResult;
import com.crab.common.jwt.JwtHelper;
import com.crab.common.jwt.JwtToken;
import com.crab.common.utils.AjaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;


/**
 * 修改后的 authc 过滤器, 添加对 AJAX 请求的支持.
 */
@Slf4j
public class RestAuthenticationFilter extends BasicHttpAuthenticationFilter {

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
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = this.getPathWithinApplication(request);

        String[] strings = path.split("==");

        if (strings.length <= 1) {
            // 普通的 URL, 正常处理
            return this.pathsMatch(strings[0], requestURI);
        } else {
            // 获取当前请求的 http method.
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
            // 匹配当前请求的 url 和 http method 与过滤器链中的的是否一致
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestURI);
        }
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        Subject subject = getSubject(servletRequest, servletResponse);

        if (!subject.isAuthenticated()) {
            AjaxUtils.writeJson(ApiResult.error("please have a login."), servletResponse);
            return false;
        }
        // 判断是否为JWT认证请求
        if (isJwtRequest(servletRequest)) {
            try {
                String jwt = getAuthzHeader(servletRequest);
                boolean bool = JwtHelper.getInstance().isTokenExpired(jwt);
                if (bool) {
                    AjaxUtils.writeJson(ApiResult.error("the token has expired"), servletResponse);
                    return false;
                }
                return true;
                //return this.checkRoles(subject,mappedValue);
            }catch (AuthenticationException e) {
                // 如果是JWT过期
                if ("expiredJwt".equals(e.getMessage())) {
                    // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的refresh时间
                    // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
                    // refresh也过期则告知客户端JWT时间过期重新认证

                    // 当存储在redis的JWT没有过期，即refresh time 没有过期
                    String appId = WebUtils.toHttp(servletRequest).getHeader("appId");
                    String jwt = getAuthzHeader(servletRequest);
                    //String refreshJwt = redisTemplate.opsForValue().get("JWT-SESSION-"+appId);
                    /*if (null != refreshJwt && refreshJwt.equals(jwt)) {
                        // 重新申请新的JWT
                        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
                        String roles = accountService.loadAccountRole(appId);
                        long refreshPeriodTime = 36000L;  //seconds为单位,10 hours
                        String newJwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(),appId,
                                "token-server",refreshPeriodTime >> 2,roles,null, SignatureAlgorithm.HS512);
                        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
                        redisTemplate.opsForValue().set("JWT-SESSION-"+appId,newJwt,refreshPeriodTime, TimeUnit.SECONDS);
                        //Message message = new Message().ok(1005,"new jwt").addData("jwt",newJwt);
                        //RequestResponseUtil.responseWrite(JSON.toJSONString(message),servletResponse);

                        AjaxUtils.writeJson(ApiResult.success(newJwt), servletResponse);
                        return false;
                    }else {
                        // jwt时间失效过期,jwt refresh time失效 返回jwt过期客户端重新登录
                        AjaxUtils.writeJson(ApiResult.error("expired jwt token."), servletResponse);
                        return false;
                    }*/
                }
            }
            AjaxUtils.writeJson(ApiResult.error("error jwt token."), servletResponse);
            return false;
        } else {
            // 请求未携带jwt 判断为无效请求
            AjaxUtils.writeJson(ApiResult.error("you must have a jwt request."), servletResponse);
            return false;
        }
    }

    private boolean isJwtRequest(ServletRequest request) {
        String authorization = getAuthzHeader(request);
        return (request instanceof HttpServletRequest)
                && !StringUtils.isEmpty(authorization);
    }

    // 验证当前用户是否属于mappedValue任意一个角色
    private boolean checkRoles(Subject subject, Object mappedValue){
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray != null && rolesArray.length > 0
                && Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }

    /**
     * 当访问被拒接时
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        // for ajax response.
        if (log.isDebugEnabled()) {
            log.debug("用户: [{}] 请求 restful url : {}, 无权限被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));
        }
        AjaxUtils.writeJson(ApiResult.error("no permission"), response);
        return false;
    }
}
