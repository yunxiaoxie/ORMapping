package com.crab.service;

import com.crab.domain.Course;
import com.crab.domain.User;
import com.crab.mapper.CourseMapper;
import com.crab.service.impl.UserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private UserImpl userImpl;

    public List<Course> getAll() {
        User u = userImpl.findUser(1);
        log.info(u.getName()+"=========================================");
        Course c = courseMapper.selectByPrimaryKey(1);
        return courseMapper.selectAll();
    }
}
