SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`                        int(11)                NOT NULL AUTO_INCREMENT      COMMENT '主键',
  `user`                    varchar(20)         NOT NULL                                     COMMENT '账号',
  `pwd`                    varchar(50)         NOT NULL                                     COMMENT '密码',
  `is_stop`                varchar(2)           DEFAULT NULL                              COMMENT '是否停用',
  `stop_time`           datetime             DEFAULT NULL                              COMMENT '停用时间',
  `stop_user`            varchar(20)         DEFAULT NULL                              COMMENT '停用人',
  `create_time`         datetime             DEFAULT NULL                              COMMENT '创建时间',
  `create_user`         varchar(20)         DEFAULT NULL                              COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'root', 'test', null, '2016-06-27 00:01:39', null, '2016-06-27 00:01:39', null);
INSERT INTO `sys_user` VALUES ('2', 'test', 'test', null, '2016-06-27 00:01:39', null, '2016-06-27 00:01:39', null);
-- ----------------------------
-- Table structure for sys_data_dic
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dic`;
CREATE TABLE `sys_data_dic` (
  `id`                        int(10)                NOT NULL AUTO_INCREMENT      COMMENT '主键',
  `pid`                      int(10)                DEFAULT NULL                              COMMENT '父主键',
  `code`                   int(10)                NOT NULL                                     COMMENT '页面通过它取值',
  `value`                  varchar(50)         NOT NULL                                     COMMENT 'value',
  `text`                    varchar(50)         DEFAULT NULL                              COMMENT 'text',
  `is_stop`                int(2)                  DEFAULT NULL                              COMMENT '是否停用(-1-停用, 空或0-启用)',
  `comet`                varchar(50)         DEFAULT NULL                              COMMENT '注释',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统字典表';

-- ----------------------------
-- Records of sys_data_dic
-- ----------------------------
INSERT INTO `sys_data_dic` VALUES ('1', null, 1001, 'Y', '是', null, '');
INSERT INTO `sys_data_dic` VALUES ('9', null, 1001, 'N', '否', null, '');
INSERT INTO `sys_data_dic` VALUES ('2', null, 1002, 'W', '女', null, '');
INSERT INTO `sys_data_dic` VALUES ('3', null, 1002, 'M', '男', null, '');
INSERT INTO `sys_data_dic` VALUES ('4', null, 1003, '1', '显示', null, '');
INSERT INTO `sys_data_dic` VALUES ('5', null, 1003, '0', '隐藏', null, '');
INSERT INTO `sys_data_dic` VALUES ('6', null, 1006, 'tree', null, null, '树型结构');
INSERT INTO `sys_data_dic` VALUES ('7', '6', 1006, 'tree1', 'christmas1', null, null);
INSERT INTO `sys_data_dic` VALUES ('8', '6', 1006, 'tree2', 'christmas2', null, null);
INSERT INTO `sys_data_dic` VALUES ('10', null, 1004, 'book', '看书', null, '');
INSERT INTO `sys_data_dic` VALUES ('11', null, 1004, 'run', '跑步', null, '');
INSERT INTO `sys_data_dic` VALUES ('12', null, 1004, 'ball', '打球', null, '');

-- ----------------------------
-- Table structure for my_user
-- ----------------------------
DROP TABLE IF EXISTS `my_user`;
CREATE TABLE `my_user` (
  `id`                        int(11)                NOT NULL AUTO_INCREMENT      COMMENT '主键',
  `name`                  varchar(50)         NOT NULL                                     COMMENT '用户名',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户测试表';


-- ----------------------------
-- Records of my_user
-- ----------------------------
INSERT INTO `my_user` VALUES ('1', 'klay', 'W', 22, '武汉市光谷大道', '13799008800', 'Y', 'book', '2016-6-6', 100.2, '2016-06-27 00:01:39');
INSERT INTO `my_user` VALUES ('2', 'Tome', 'M', 22, '武汉市光谷大道', '18988991234', 'Y', 'book,run', '2016-6-6', 100.3, '2016-06-27 00:35:28');
INSERT INTO `my_user` VALUES ('3', 'aa', 'M', 22, '武汉市光谷大道', '18988991234', 'Y', '', '2016-6-6', 100.3, '2016-06-27 00:35:28');
INSERT INTO `my_user` VALUES ('4', 'bb', 'M', 22, '武汉市光谷大道', '18988991234', 'Y', 'book', '2016-6-6', 100.3, '2016-06-27 00:35:28');
INSERT INTO `my_user` VALUES ('5', 'dd', 'M', 22, '武汉市光谷大道', '18988991234', 'Y', 'book', '2016-6-6', 100.3, '2016-06-27 00:35:28');
INSERT INTO `my_user` VALUES ('6', 'cc', 'M', 22, '武汉市光谷大道', '18988991234', 'Y', 'book', '2016-6-6', 100.3, '2016-06-27 00:35:28');


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
INSERT INTO `boot_user` VALUES ('3', 'aa', '13799008800', '2016-06-27 00:01:39');
INSERT INTO `boot_user` VALUES ('4', 'bb', '18988991234', '2016-06-27 00:35:28');
INSERT INTO `boot_user` VALUES ('5', 'cc', '13799008800', '2016-06-27 00:01:39');
INSERT INTO `boot_user` VALUES ('6', 'dd', '18988991234', '2016-06-27 00:35:28');
INSERT INTO `boot_user` VALUES ('7', 'ee', '13799008800', '2016-06-27 00:01:39');
INSERT INTO `boot_user` VALUES ('8', 'ff', '18988991234', '2016-06-27 00:35:28');
