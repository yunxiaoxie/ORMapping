package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.domain.Course;
import com.crab.domain.CourseChapter;
import com.crab.service.CourseChapterService;
import com.crab.service.CourseService;
import com.crab.vo.CourseChapterVo;
import com.crab.vo.SelectVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
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
    @Autowired
    private CourseChapterService courseChapterService;

    //TODO 角色注解
    @ApiOperation(value = "查询所有课程信息", notes = "查询所有课程信息")
    @GetMapping("all")
    public ApiResult findCourseAll() {
        log.info("查询所有课程");
        return ApiResult.success(courseService.getAll());
    }

    @ApiOperation(value = "查询所有课程章节信息", notes = "查询所有课程章节信息")
    @GetMapping("chapter/all")
    public ApiResult findCourseChapterAll() {
        log.info("查询所有课程章节");
        return ApiResult.success(courseChapterService.getAll());
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

    //@RequiresRoles(value={"user","admin"},logical=Logical.OR)  //or
    //@RequiresRoles({"user","admin"})   //and
    @ApiOperation(value = "通过id查询课程", notes = "通过id查询课程")
    @RequiresPermissions("Export")
    @RequiresRoles("teacher")
    @RequiresAuthentication
    @RequiresUser
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
                                      @ApiParam(value="章节内容说明") @RequestParam(value="videoComm", required = false) String videoComm,
                                      @ApiParam(value="课程id",required = true) @RequestParam(value="courseId") Integer courseId,
                                      @ApiParam(value="视频地址", required=true) @RequestParam(value="videoUrl") String videoUrl,
                                      @ApiParam(value="视频提取码", required=true) @RequestParam(value="videoCode") String videoCode,
                                      @ApiParam(value="源码地址") @RequestParam(value="sourcecodeUrl",required = false) String sourcecodeUrl,
                                      @ApiParam(value="源码提取码") @RequestParam(value="sourcecodeCode", required = false) String sourcecodeCode) {
        log.info("addCourse: {} {} {} {} {} {}", chapter, id, videoUrl, videoCode, sourcecodeCode, sourcecodeUrl);
        CourseChapter course = new CourseChapter();
        course.setId(id);
        course.setChapter(chapter);
        course.setCourseId(courseId);
        course.setVideoUrl(videoUrl);
        course.setVideoCode(videoCode);
        course.setVideoComm(videoComm);
        course.setSourcecodeUrl(sourcecodeUrl);
        course.setSourcecodeCode(sourcecodeCode);
        courseChapterService.insertOrUpdate(course);
        return ApiResult.success();
    }

    @ApiOperation(value = "分页查询所有课程章节", notes = "分页查询所有课程章节")
    @RequiresPermissions("Export")
    @GetMapping("chapter/list")
    public ApiResult getCourseChapterList(@ApiParam(value="起始页", required=true) @RequestParam(value="page") Integer page,
                                   @ApiParam(value="每页数", required=true) @RequestParam(value="limit") Integer limit) {
        log.info("分页查询所有课程章节");
        PageHelper.startPage(page, limit);
        List<CourseChapterVo> list = courseChapterService.getAll();
        PageInfo<CourseChapterVo> pageInfo = new PageInfo<>(list);
        return ApiResult.success(pageInfo);
    }

    @ApiOperation(value = "通过id查询课程章节", notes = "通过id查询课程章节")
    @RequiresPermissions("Export")
    @GetMapping("chapter/detail")
    public ApiResult getCourseChapter(@ApiParam(value="id", required=true) @RequestParam(value="id") Integer id) {
        log.info("通过id查询课程,{}",id);
        CourseChapter course = courseChapterService.getChapterById(id);
        return ApiResult.success(course);
    }
}
