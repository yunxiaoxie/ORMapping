package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.domain.User;
import com.crab.service.CourseService;
import com.crab.service.IUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/")
@Api("登录相关api")
public class LoginController {

    @Autowired
    private IUser user;

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
}
