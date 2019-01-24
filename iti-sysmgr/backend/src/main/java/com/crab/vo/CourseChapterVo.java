package com.crab.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseChapterVo {
    private Integer id;

    private String course;

    private Integer period;

    private Integer courseId;

    private String chapter;

    private String videoUrl;

    private String videoCode;

    private String videoComm;

    private String sourcecodeUrl;

    private String sourcecodeCode;

    private Date createTime;

    private String createUser;

}
