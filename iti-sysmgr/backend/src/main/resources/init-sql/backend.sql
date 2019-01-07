SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id`                    int(10)                NOT NULL AUTO_INCREMENT                    COMMENT '主键',
  `course`                varchar(20)            NOT NULL                                   COMMENT '课程名称',
  `period`                int(2)                 NOT NULL                                   COMMENT '课程期数',
  `create_time`           timestamp              DEFAULT CURRENT_TIMESTAMP                  COMMENT '创建时间',
  `create_user`           varchar(20)            DEFAULT NULL                               COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='录课表';

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('1', '前端高级进阶', '1', null, null);
INSERT INTO `t_course` VALUES ('2', '前端初中级', '1', null, null);
-- ----------------------------
-- Table structure for t_course_chapter
-- ----------------------------
DROP TABLE IF EXISTS `t_course_chapter`;
CREATE TABLE `t_course_chapter` (
  `id`                       int(10)               NOT NULL AUTO_INCREMENT                    COMMENT '主键',
  `course_id`                int(10)               DEFAULT NULL                               COMMENT '课程主键',
  `chapter`                  varchar(20)           NOT NULL                                   COMMENT '章节名称',
  `video_url`                varchar(50)           NOT NULL                                   COMMENT '章节视频地址',
  `video_code`               varchar(20)           NOT NULL                                   COMMENT '提取码',
  `sourcecode_url`           varchar(50)           NOT NULL                                   COMMENT '章节代码地址',
  `sourcecode_code`          varchar(20)           NOT NULL                                   COMMENT '提取码',
  `create_time`              timestamp             DEFAULT CURRENT_TIMESTAMP                  COMMENT '创建时间',
  `create_user`              varchar(20)           DEFAULT NULL                               COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='录课章节表';

-- ----------------------------
-- Records of t_course_chapter
-- ----------------------------


-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `id`                        int(11)                NOT NULL AUTO_INCREMENT                    COMMENT '主键',
  `name`                      varchar(50)            NOT NULL                                   COMMENT '用户名',
  `qqwx`                      varchar(50)            NOT NULL UNIQUE                            COMMENT '用户名(QQ/WX)',
  `course_id`                 int(10)                NOT NULL                                   COMMENT '课程',
  `create_time`               timestamp              DEFAULT CURRENT_TIMESTAMP                  COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='学生查询下载表';


-- ----------------------------
-- Records of t_student
-- ----------------------------


