package com.crab.service;

import com.crab.common.Constant;
import com.crab.domain.Coursechapter;
import com.crab.domain.Teacher;
import com.crab.domain.User;
import com.crab.mapper.CoursechapterMapper;
import com.crab.mapper.TeacherMapper;
import com.crab.vo.CourseChapterVo;
import com.crab.vo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseChapterService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private CoursechapterMapper coursechapterMapper;

    public List<CourseChapterVo> getAll() {
        LoginInfo loginInfo = (LoginInfo)SecurityUtils.getSubject().getPrincipal();
        //角色划分
        //Constant.ADMIN.equals(loginInfo.getRoles().get(0).getRoleName())
        if (SecurityUtils.getSubject().hasRole(Constant.ADMIN)) {
            //管理员
            return coursechapterMapper.selectAll();
        } else {
            //授权课
            Teacher teacher = teacherMapper.findByUserId(loginInfo.getUser().getId());
            Integer courseId = Integer.parseInt(teacher.getCourseId());
            return coursechapterMapper.selectAllByCourseId(courseId);
        }
    }
    public List<CourseChapterVo> selectAllByCourseId(Integer courseId) {
        return coursechapterMapper.selectAllByCourseId(courseId);
    }

    public void insertOrUpdate(Coursechapter course){
        LoginInfo loginInfo = (LoginInfo)SecurityUtils.getSubject().getPrincipal();
        course.setUserId(loginInfo.getUser().getId());
        if (course.getId() == null) {
            coursechapterMapper.insert(course);
        } else {
            coursechapterMapper.updateByPrimaryKey(course);
        }
    }

    public Coursechapter getChapterById(Integer id) {
        return coursechapterMapper.selectByPrimaryKey(id);
    }

}
