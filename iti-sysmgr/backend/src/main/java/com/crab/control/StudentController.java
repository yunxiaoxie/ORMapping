package com.crab.control;

import com.crab.domain.Student;
import com.crab.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("student")
@Api("学员相关api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "查询所有学生信息", notes = "查询所有学生信息")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Student> findCourseAll() {
        log.info("查询所有学员");
        return studentService.getAll();
    }
}
