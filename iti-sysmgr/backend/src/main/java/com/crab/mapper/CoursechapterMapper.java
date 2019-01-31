package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Coursechapter;
import com.crab.vo.CourseChapterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoursechapterMapper extends GenericMapper<Coursechapter, Integer> {

    List<CourseChapterVo> selectAll();

    List<CourseChapterVo> selectAllByCourseId(@Param("id") Integer courseId);
}