package com.crab.service;

import com.crab.domain.Student;
import com.crab.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    public List<Student> getAll() {
        return studentMapper.selectAll();
    }
}
