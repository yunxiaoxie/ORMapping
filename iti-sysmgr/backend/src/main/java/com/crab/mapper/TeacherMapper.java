package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Student;
import com.crab.domain.Teacher;
import io.swagger.models.auth.In;

import java.util.List;

public interface TeacherMapper extends GenericMapper<Teacher, Integer> {
    List<Teacher> selectAll();

    /**
     * 根据user_id查询老师所属课程
     */
    List<Teacher> findByUserId(Integer userId);
}