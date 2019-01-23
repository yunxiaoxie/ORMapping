package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.domain.Course;
import com.crab.domain.Coursechapter;
import com.crab.service.CourseChapterService;
import com.crab.service.CourseService;
import com.crab.vo.SelectVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@Transactional
@RestController
@RequestMapping("course")
@Api("课程相关api")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseChapterService courseChapterService;

    @ApiOperation(value = "查询所有课程信息", notes = "查询所有课程信息")
    @GetMapping("all")
    public ApiResult findCourseAll() {
        log.info("查询所有课程");
        return ApiResult.success(courseService.getAll());
    }

    @ApiOperation(value = "添加或更新课程", notes = "添加或更新课程")
    @RequiresPermissions("Export")
    @PostMapping("addOrUpd")
    public ApiResult addCourse(@ApiParam(value="课程名称", required=true) @RequestParam(value="course") String courseName,
                               @ApiParam(value="课程id") @RequestParam(value="id", required = false) Integer id,
                               @ApiParam(value="课程期数", required=true) @RequestParam(value="period") Integer period) {
        log.info("addCourse: {} {} {}", courseName, id, period);
        Course course = new Course();
        course.setId(id);
        course.setCourse(courseName);
        course.setPeriod(period);
        courseService.insertOrUpdate(course);
        return ApiResult.success();
    }

    @ApiOperation(value = "通过id查询课程", notes = "通过id查询课程")
    @RequiresPermissions("Export")
    @GetMapping("detail")
    public ApiResult getCourse(@ApiParam(value="id", required=true) @RequestParam(value="id") Integer id) {
        log.info("通过id查询课程,{}",id);
        Course course = courseService.getCourseById(id);
        return ApiResult.success(course);
    }

    @ApiOperation(value = "分页查询所有课程", notes = "分页查询所有课程")
    @RequiresPermissions("Export")
    @GetMapping("list")
    public ApiResult getCourseList(@ApiParam(value="起始页", required=true) @RequestParam(value="page") Integer page,
                                   @ApiParam(value="每页数", required=true) @RequestParam(value="limit") Integer limit) {
        log.info("分页查询所有课程");
        PageHelper.startPage(page, limit);
        List<Course> list = courseService.getCourseList();
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        return ApiResult.success(pageInfo);
    }

    @ApiOperation(value = "查询课程名称", notes = "查询课程名称")
    @GetMapping("names")
    public ApiResult getCourseNames() {
        log.info("查询课程名称");
        List<SelectVo> list = courseService.getCourseName();
        return ApiResult.success(list);
    }

    @ApiOperation(value = "添加或更新课程章节", notes = "添加或更新课程章节")
    @PostMapping("chapter/addOrUpd")
    public ApiResult addCourseChapter(@ApiParam(value="章节名称", required=true) @RequestParam(value="chapter") String chapter,
                                      @ApiParam(value="章节id") @RequestParam(value="id", required = false) Integer id,
                                      @ApiParam(value="课程id",required = true) @RequestParam(value="course_id") Integer course_id,
                                      @ApiParam(value="视频地址", required=true) @RequestParam(value="video_url") String video_url,
                                      @ApiParam(value="视频提取码", required=true) @RequestParam(value="video_code") String video_code,
                                      @ApiParam(value="源码地址") @RequestParam(value="sourcecode_url",required = false) String sourcecode_url,
                                      @ApiParam(value="源码提取码") @RequestParam(value="sourcecode_code", required = false) String sourcecode_code) {
        log.info("addCourse: {} {} {} {} {} {}", chapter, id, video_url, video_code, sourcecode_code, sourcecode_url);
        Coursechapter course = new Coursechapter();
        course.setId(id);
        course.setChapter(chapter);
        course.setCourseId(course_id);
        course.setVideoUrl(video_url);
        course.setVideoCode(video_code);
        course.setSourcecodeUrl(sourcecode_url);
        course.setSourcecodeCode(sourcecode_code);
        courseChapterService.insertOrUpdate(course);
        return ApiResult.success();
    }
}
