package com.crab.service;

import com.crab.common.Constant;
import com.crab.domain.Coursechapter;
import com.crab.domain.Teacher;
import com.crab.mapper.CourseChapterMapper;
import com.crab.mapper.TeacherMapper;
import com.crab.vo.CourseChapterVo;
import com.crab.vo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseChapterService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private CourseChapterMapper coursechapterMapper;

    public List<CourseChapterVo> getAll() {
        LoginInfo loginInfo = (LoginInfo)SecurityUtils.getSubject().getPrincipal();
        //角色划分
        //Constant.ADMIN.equals(loginInfo.getRoles().get(0).getRoleName())
        if (SecurityUtils.getSubject().hasRole(Constant.ADMIN)) {
            //管理员
            return coursechapterMapper.selectAll();
        } else {
            //授权课，一个老师有多个课，一个学生也有多个课
            List<Teacher> teacherList = teacherMapper.findByUserId(loginInfo.getUser().getId());
            List<Integer> courseId = teacherList.stream().map(teacher ->
                Integer.parseInt(teacher.getCourseId())
            ).collect(Collectors.toList());
            return coursechapterMapper.selectAllByCourseIds(courseId);
        }
    }
    public List<CourseChapterVo> selectAllByCourseId(Integer courseId) {
        List<Integer> ids = Arrays.asList(courseId);
        return coursechapterMapper.selectAllByCourseIds(ids);
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
