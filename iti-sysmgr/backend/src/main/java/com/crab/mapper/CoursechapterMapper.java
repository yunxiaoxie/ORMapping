package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Coursechapter;
import com.crab.vo.CourseChapterVo;

import java.util.List;

public interface CoursechapterMapper extends GenericMapper<Coursechapter, Integer> {

    List<CourseChapterVo> selectAll();
}