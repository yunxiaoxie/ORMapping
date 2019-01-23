package com.crab.service;

import com.crab.domain.Coursechapter;
import com.crab.mapper.CoursechapterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseChapterService {
    @Resource
    private CoursechapterMapper coursechapterMapper;

    public List<Coursechapter> getAll() {
        return coursechapterMapper.selectAll();
    }

    public void insertOrUpdate(Coursechapter course){
        if (course.getId() == null) {
            coursechapterMapper.insert(course);
        } else {
            coursechapterMapper.updateByPrimaryKey(course);
        }
    }

    public List<Coursechapter> getCourseList() {
        return coursechapterMapper.selectAll();
    }

    public Coursechapter getCourseById(Integer id) {
        return coursechapterMapper.selectByPrimaryKey(id);
    }

}
