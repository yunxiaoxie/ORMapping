package com.crab.service;

import com.crab.domain.Student;
import com.crab.mapper.CoursechapterMapper;
import com.crab.mapper.StudentMapper;
import com.crab.vo.CourseChapterVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private CoursechapterMapper coursechapterMapper;

    public List<Student> getAll() {
        return studentMapper.selectAll();
    }

    public List<CourseChapterVo> getByCourseIds(List<Integer> ids) {
        return coursechapterMapper.selectAllByCourseIds(ids);
    }
}
