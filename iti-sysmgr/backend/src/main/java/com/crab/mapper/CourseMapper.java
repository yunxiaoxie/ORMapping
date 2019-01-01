package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Course;

import java.util.List;

public interface CourseMapper extends GenericMapper<Course, Integer> {

    List<Course> selectAll();
}