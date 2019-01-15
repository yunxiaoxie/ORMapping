package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.domain.Course;
import com.crab.service.CourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Transactional
@RestController
@RequestMapping("course")
@Api("课程相关api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "查询所有课程信息", notes = "查询所有课程信息")
    @GetMapping("all")
    public ApiResult findCourseAll() {
        log.info("查询所有课程");
        return ApiResult.success(courseService.getAll());
    }

    @ApiOperation(value = "添加课程", notes = "添加课程")
    @PostMapping("add")
    public ApiResult addCourse(@ApiParam(value="课程名称", required=true) @RequestParam(value="course") String courseName,
                               @ApiParam(value="课程期数", required=true) @RequestParam(value="period") Integer period) {
        log.info("添加课程");
        Course course = new Course();
        course.setCourse(courseName);
        course.setPeriod(period);
        courseService.insert(course);
        return ApiResult.success();
    }

    @ApiOperation(value = "分页查询所有课程", notes = "分页查询所有课程")
    @GetMapping("list")
    public ApiResult getCourseList(@ApiParam(value="起始页", required=true) @RequestParam(value="page") Integer page,
                                   @ApiParam(value="每页数", required=true) @RequestParam(value="limit") Integer limit) {
        log.info("分页查询所有课程");
        PageHelper.startPage(page, limit);
        List<Course> list = courseService.getCourseList();
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        return ApiResult.success(pageInfo);
    }
}
