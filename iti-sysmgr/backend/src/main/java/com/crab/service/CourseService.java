package com.crab.service;

import com.crab.domain.Course;
import com.crab.domain.User;
import com.crab.mapper.CourseMapper;
import com.crab.service.impl.UserImpl;
import com.crab.vo.SelectVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    public List<Course> getAll() {
        return courseMapper.selectAll();
    }

    public void insertOrUpdate(Course course){
        if (course.getId() == null) {
            courseMapper.insert(course);
        } else {
            courseMapper.updateByPrimaryKey(course);
        }
    }

    public List<Course> getCourseList() {
        return courseMapper.selectAll();
    }

    public Course getCourseById(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    /**
     * 得到课程名称和值，如:前端高级进阶[期数]-id
     * */
    public List<SelectVo> getCourseName() {
        List<Course> list = courseMapper.selectAll();
        List<SelectVo> result = list.stream()
                .map(course -> {
                    String label = course.getCourse()+"["+course.getPeriod()+"]";
                    return new SelectVo(label, course.getId());
                })
                .collect(Collectors.toList());
        return result;
    }

}
