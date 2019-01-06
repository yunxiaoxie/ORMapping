package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.common.MapCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
@Api("登录相关api")
public class UserController {

    @ApiOperation("用户信息(角色,权限等)")
    @GetMapping("info")
    public ApiResult userInfo(@ApiParam(value="token", required=true) @RequestParam(value="token") String token) {
        log.info("用户信息(角色,权限等)");

        Map<String, Object> map = MapCache.getInstance().get(token);

        return ApiResult.success(map);
    }
}
