package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Student;

import java.util.List;

public interface StudentMapper extends GenericMapper<Student, Integer> {
    List<Student> selectAll();
}