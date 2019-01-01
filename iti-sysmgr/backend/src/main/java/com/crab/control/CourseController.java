package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("course")
@Api("课程相关api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "查询所有课程信息", notes = "查询所有课程信息")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ApiResult findCourseAll() {
        log.info("查询所有课程");
        return ApiResult.success(courseService.getAll());
    }
}
