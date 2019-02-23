package com.crab.common.jwt;

import com.crab.common.ApiResult;
import com.crab.common.shiro.filter.RestPathMatchingFilter;
import com.crab.common.utils.AjaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Stream;

/**
 * jwt filter, 相比BasicHttpAuthenticationFilter不用设置跨域处理。
 */
@Slf4j
public class RestJwtFilter extends RestPathMatchingFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        // 判断是否为JWT认证请求
        if ((null != subject && !subject.isAuthenticated()) && isJwtSubmission(servletRequest)) {
            AuthenticationToken token = createJwtToken(servletRequest);
            try {
                subject.login(token);
                return this.checkRoles(subject,mappedValue);
            }catch (AuthenticationException e) {
                // 如果是JWT过期
                if ("expiredJwt".equals(e.getMessage())) {
                    // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的refresh时间
                    // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
                    // refresh也过期则告知客户端JWT时间过期重新认证

                    // 当存储在redis的JWT没有过期，即refresh time 没有过期
                    String appId = WebUtils.toHttp(servletRequest).getHeader("appId");
                    String jwt = WebUtils.toHttp(servletRequest).getHeader("Authorization");
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

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);

        // 未认证的情况
        if (null == subject || !subject.isAuthenticated()) {
            // 告知客户端JWT认证失败需跳转到登录页面
            AjaxUtils.writeJson(ApiResult.error("error jwt."), servletResponse);
        }else {
            //  已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            AjaxUtils.writeJson(ApiResult.error("no permission."), servletResponse);
        }
        // 过滤链终止
        return false;
    }

    /**
     * 判断是否为jwt请求
     */
    private boolean isJwtSubmission(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
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
     *生成token
     */
    private AuthenticationToken createJwtToken(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ipHost = httpRequest.getRemoteAddr();
        String jwt = httpRequest.getHeader("Authorization");

        return new JwtToken(jwt,ipHost);
    }
}
