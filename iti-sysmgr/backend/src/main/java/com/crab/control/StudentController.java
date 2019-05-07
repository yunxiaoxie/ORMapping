package com.crab.control;

import com.crab.common.ApiResult;
import com.crab.domain.Student;
import com.crab.service.CourseChapterService;
import com.crab.service.StudentService;
import com.crab.vo.CourseChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("student")
@Api("学员相关api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequiresRoles("student")
    @ApiOperation(value = "查询所有学生信息", notes = "查询所有学生信息")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ApiResult findCourseAll() {
        log.info("查询所有学员");
        List<Student> list = studentService.getAll();
        return ApiResult.success(list);
    }

    @ApiOperation(value = "查询学生所属课程", notes = "查询学生所属课程")
    @RequestMapping(value = "course-chapter", method = RequestMethod.GET)
    public ApiResult findCourseAllById(@ApiParam(value="courseId", required=true) @RequestParam(value="courseId") Integer courseId) {
        log.info("查询学生所属课程");
        List<Integer> ids = Arrays.asList(courseId);
        List<CourseChapterVo> list = studentService.getByCourseIds(ids);
        return ApiResult.success(list);
    }
}
