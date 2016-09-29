SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for my_user
-- ----------------------------
DROP TABLE IF EXISTS `my_user`;
CREATE TABLE `my_user` (
  `id`                        int(11)                NOT NULL AUTO_INCREMENT      COMMENT '主键',
  `name`                  varchar(50)         DEFAULT NULL                              COMMENT '用户名',
  `sex`                      varchar(2)           DEFAULT NULL                              COMMENT '性别',
  `age`                     int(3)                   DEFAULT NULL                              COMMENT '年龄',
  `addr`                    varchar(100)       DEFAULT NULL                              COMMENT '地址',
  `tel`                       varchar(11)         DEFAULT NULL                              COMMENT '电话',
  `is_adult`               varchar(2)           DEFAULT NULL                              COMMENT '是否成人',
  `intrest`                 varchar(100)       DEFAULT NULL                              COMMENT '兴趣',
  `birthday`              date                    DEFAULT NULL                              COMMENT '生日',
  `weight`                 double                DEFAULT NULL                              COMMENT '体重',
  `create_time`         datetime             DEFAULT NULL                              COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户测试表';


-- ----------------------------
-- Records of my_user
-- ----------------------------
INSERT INTO `my_user` VALUES ('1', 'klay', 'W', 22, '武汉市光谷大道', '13799008800', 'Y', '', '2016-6-6', 100.2, '2016-06-27 00:01:39');
INSERT INTO `my_user` VALUES ('2', 'Tome', 'M', 22, '武汉市光谷大道', '18988991234', 'Y', '', '2016-6-6', 100.3, '2016-06-27 00:35:28');


-- ----------------------------
-- Table structure for `boot_user`
-- ----------------------------
DROP TABLE IF EXISTS `boot_user`;
CREATE TABLE `boot_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of boot_user
-- ----------------------------
INSERT INTO `boot_user` VALUES ('1', 'klay', '13799008800', '2016-06-27 00:01:39');
INSERT INTO `boot_user` VALUES ('2', 'Tome', '18988991234', '2016-06-27 00:35:28');
