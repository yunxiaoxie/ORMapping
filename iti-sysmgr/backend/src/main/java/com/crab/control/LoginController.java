package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.common.MapCache;
import com.crab.common.jwt.JwtHelper;
import com.crab.common.vo.AccessToken;
import com.crab.common.vo.Audience;
import com.crab.domain.Role;
import com.crab.domain.User;
import com.crab.service.IRole;
import com.crab.service.IUser;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/")
@Api("登录相关api")
public class LoginController {

    @Autowired
    private IUser user;
    @Autowired
    private IRole roleService;
    @Autowired
    private Audience audience;

    @ApiOperation("系统登录")
    @PostMapping("login/simple")
    public ApiResult login(@ApiParam(value="账号", required=true) @RequestParam(value="account") String account,
                           @ApiParam(value="密码", required=true) @RequestParam(value="password") String password) {
        log.info("系统登录");
        User u = user.findUser(account, password);
        if (u == null) {
            return ApiResult.error("无效账号");
        }
        //remove password
        u.setPassword(null);
        return ApiResult.success(u.toString());
    }

    @ApiOperation("系统登录")
    @PostMapping("login")
    public ApiResult loginShiro(@ApiParam(value="账号", required=true) @RequestParam(value="account") String account,
                           @ApiParam(value="密码", required=true) @RequestParam(value="password") String password) {
        log.info("系统登录byShiro");
//        UsernamePasswordToken uptoken = new UsernamePasswordToken(account,password);
//        Subject subject = SecurityUtils.getSubject();
        //subject.login(uptoken);

        User u = user.findUser(account, password);
        u.setPassword(null);
        List<Role> list = roleService.searchRolesOfUser(u.getId());
        List<String> roles = list.stream().map(role -> role.getRoleName()).collect(Collectors.toList());

        // get accessToken
        JwtHelper.getInstance().setAudience(audience);
        AccessToken token = JwtHelper.getInstance().accessToken(account, null, null);

        //set cache
        Map<String, Object> map = Maps.newHashMap();
        map.put(MapCache.USER, u);
        map.put(MapCache.USER_ROLE, roles);
        map.put(MapCache.USER_TOKEN, token);
        MapCache.getInstance().put(token.getToken(), map);

        return ApiResult.success(token);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
            User user = (User)subject.getPrincipals().getPrimaryPrincipal();
            //userService.deleteLoginInfo(user.getUsername());
        }
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok().build();
    }

}
