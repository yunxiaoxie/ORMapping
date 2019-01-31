SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `p_permission`
-- ----------------------------
DROP TABLE IF EXISTS `p_permission`;
CREATE TABLE `p_permission` (
  `id`              int(11)            NOT NULL AUTO_INCREMENT,
  `name`            varchar(50)        UNIQUE NOT NULL                    COMMENT '权限名',
  `sn`              int(2)             NOT NULL UNIQUE                    COMMENT '权限标识(BIT)',
  `comment`         varchar(50)        DEFAULT NULL                       COMMENT '权限说明',
  `create_time`     timestamp          DEFAULT CURRENT_TIMESTAMP          COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '权限表';

-- ----------------------------
-- Records of p_permission
-- ----------------------------
INSERT INTO `p_permission` VALUES ('1', 'query', 0, '查询', null);
INSERT INTO `p_permission` VALUES ('2', 'create', 1, '增加', null);
INSERT INTO `p_permission` VALUES ('3', 'update', 2, '修改', null);
INSERT INTO `p_permission` VALUES ('4', 'delete', 3, '删除', null);

-- ----------------------------
-- Table structure for `p_acl`
-- ----------------------------
DROP TABLE IF EXISTS `p_acl`;
CREATE TABLE `p_acl` (
  `id`                 int(11)             NOT NULL AUTO_INCREMENT,
  `principal_type`     varchar(50)         NOT NULL                        COMMENT '主体类型(角色或用户)',
  `principal_sn`       int(11)             NOT NULL                        COMMENT '主体标识(ID)',
  `resource_sn`        int(11)             NOT NULL                        COMMENT '资源标识(Module ID)',
  `acl_state`          int(32)             NOT NULL                        COMMENT '授权状态(用(bit)表示, 64bit-20位max)',
  `acl_ext_state`      int(2)              DEFAULT 0                       COMMENT '是否继承(0否-1是)',
  `create_time`        timestamp          DEFAULT CURRENT_TIMESTAMP        COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '访问控制列表';

-- ----------------------------
-- Records of p_acl
-- ----------------------------
INSERT INTO `p_acl` VALUES ('1', 'ROLE', 1, 1, 15, 0, null);
INSERT INTO `p_acl` VALUES ('2', 'ROLE', 1, 2, 15, 0, null);
INSERT INTO `p_acl` VALUES ('3', 'ROLE', 1, 3, 15, 0, null);
INSERT INTO `p_acl` VALUES ('4', 'ROLE', 1, 4, 15, 0, null);
INSERT INTO `p_acl` VALUES ('5', 'ROLE', 1, 5, 15, 0, null);
INSERT INTO `p_acl` VALUES ('6', 'ROLE', 1, 6, 15, 0, null);
INSERT INTO `p_acl` VALUES ('7', 'ROLE', 1, 7, 15, 0, null);
INSERT INTO `p_acl` VALUES ('8', 'ROLE', 1, 8, 15, 0, null);
INSERT INTO `p_acl` VALUES ('9', 'ROLE', 1, 9, 15, 0, null);
INSERT INTO `p_acl` VALUES ('10', 'ROLE', 1, 10, 15, 0, null);
INSERT INTO `p_acl` VALUES ('11', 'ROLE', 1, 11, 15, 0, null);
INSERT INTO `p_acl` VALUES ('12', 'ROLE', 1, 12, 15, 0, null);
INSERT INTO `p_acl` VALUES ('13', 'ROLE', 2, 1, 9, 0, null);
INSERT INTO `p_acl` VALUES ('16', 'ROLE', 2, 4, 15, 0, null);
INSERT INTO `p_acl` VALUES ('14', 'ROLE', 3, 1, 5, 0, null);
INSERT INTO `p_acl` VALUES ('15', 'USER', 4, 1, 1, 0, null);

-- ----------------------------
-- Table structure for `p_module`
-- ----------------------------
DROP TABLE IF EXISTS `p_module`;
CREATE TABLE `p_module` (
  `id`              int(11)            NOT NULL AUTO_INCREMENT,
  `pid`             int(10)            DEFAULT NULL                     COMMENT '父模块',
  `name`            varchar(32)        NOT NULL                         COMMENT '模块名称',
  `url`             varchar(50)        DEFAULT NULL                     COMMENT '模块地址',
  `order_no`        int(10)            DEFAULT NULL                     COMMENT '模块排序编号',
  `sn`              varchar(32)        NOT NULL UNIQUE                  COMMENT '模块标识',
  `create_time`     timestamp          DEFAULT CURRENT_TIMESTAMP        COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '系统模块';

-- ----------------------------
-- Records of p_module
-- ----------------------------
INSERT INTO `p_module` VALUES ('1', null, 'Overview', '/index', '1', 'IndexUrl', null);
INSERT INTO `p_module` VALUES ('2', null, 'Course', '/course/add', '1', 'Reports', null);
INSERT INTO `p_module` VALUES ('3', null, 'Course', '/course/list', '1', 'Analytics', null);
INSERT INTO `p_module` VALUES ('4', null, 'Export', '/export', '1', 'Export', null);
INSERT INTO `p_module` VALUES ('5', null, 'Nav item', '/navitem', '1', 'Nav_item', null);
INSERT INTO `p_module` VALUES ('6', null, 'Nav item again', '/navitemagain', '1', 'Nav_item_again', null);
INSERT INTO `p_module` VALUES ('7', null, 'One more nav', '/onenav', '1', 'One_more_nav', null);
INSERT INTO `p_module` VALUES ('8', null, 'Another nav item', '/anothernav', '1', 'Another_nav_item', null);
INSERT INTO `p_module` VALUES ('9', null, 'More navigation', '/more', '1', 'More_navigation', null);
INSERT INTO `p_module` VALUES ('10', null, 'Acl Manager', '/acl', '1', 'Acl_Manager', null);
INSERT INTO `p_module` VALUES ('11', null, 'One more nav', null, '1', 'One_more_nav1', null);
INSERT INTO `p_module` VALUES ('12', null, 'Another nav item', null, '1', 'Another_nav_item1', null);

-- ----------------------------
-- Table structure for `p_role`
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role` (
  `id`                int(11)              NOT NULL AUTO_INCREMENT,
  `role_name`         varchar(32)          DEFAULT NULL UNIQUE                COMMENT '角色名称',
  `description`       varchar(200)         DEFAULT NULL                       COMMENT '角色描述',
  `create_time`       timestamp            DEFAULT CURRENT_TIMESTAMP          COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT '角色表';

-- ----------------------------
-- Records of p_role
-- ----------------------------
INSERT INTO `p_role` VALUES ('1', 'admin', '系统管理员', null);
INSERT INTO `p_role` VALUES ('2', 'teacher', '教师', null);
INSERT INTO `p_role` VALUES ('3', 'student', '学生', null);

-- ----------------------------
-- Table structure for `p_role_module`
-- ----------------------------
DROP TABLE IF EXISTS `p_role_module`;
CREATE TABLE `p_role_module` (
  `id`               int(11)             NOT NULL AUTO_INCREMENT,
  `role_id`          int(11)             DEFAULT NULL                 COMMENT '角色id',
  `module_id`        int(11)             DEFAULT NULL                 COMMENT '模块id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '角色模块映射表';

-- ----------------------------
-- Records of p_role_module
-- ----------------------------
INSERT INTO `p_role_module` VALUES ('1', '1', '1');
INSERT INTO `p_role_module` VALUES ('2', '1', '2');
INSERT INTO `p_role_module` VALUES ('3', '1', '3');
INSERT INTO `p_role_module` VALUES ('4', '1', '4');
INSERT INTO `p_role_module` VALUES ('5', '1', '5');
INSERT INTO `p_role_module` VALUES ('6', '1', '6');
INSERT INTO `p_role_module` VALUES ('7', '1', '7');
INSERT INTO `p_role_module` VALUES ('8', '1', '8');
INSERT INTO `p_role_module` VALUES ('9', '1', '9');
INSERT INTO `p_role_module` VALUES ('10', '1', '10');
INSERT INTO `p_role_module` VALUES ('11', '1', '11');
INSERT INTO `p_role_module` VALUES ('12', '1', '12');
INSERT INTO `p_role_module` VALUES ('13', '2', '1');
INSERT INTO `p_role_module` VALUES ('14', '2', '2');
INSERT INTO `p_role_module` VALUES ('15', '3', '1');
INSERT INTO `p_role_module` VALUES ('16', '2', '4');

-- ----------------------------
-- Table structure for `p_user`
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user` (
  `id`                int(11)              NOT NULL AUTO_INCREMENT,
  `account`           varchar(32)          UNIQUE NOT NULL                  COMMENT '账号',
  `password`          varchar(32)          NOT NULL                         COMMENT '密码',
  `name`              varchar(32)          DEFAULT NULL                     COMMENT '用户名称',
  `stop_state`        varchar(2)           DEFAULT NULL                     COMMENT '是否停用',
  `stop_user`         varchar(20)          DEFAULT NULL                     COMMENT '停用人',
  `create_time`       timestamp            DEFAULT CURRENT_TIMESTAMP        COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '用户表';

-- ----------------------------
-- Records of p_user
-- ----------------------------
INSERT INTO `p_user` VALUES ('1', 'admin', 'admin@123', 'Admin', null, null, null);
INSERT INTO `p_user` VALUES ('2', 'jack', 'jack@123', 'Lance', null, null, null);
INSERT INTO `p_user` VALUES ('3', 'test', 'test', 'test', null, null, null);
INSERT INTO `p_user` VALUES ('4', 'test4', 'test', 'noRole', null, null, null);

-- ----------------------------
-- Table structure for `p_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `p_user_role`;
CREATE TABLE `p_user_role` (
  `id`              int(11)            NOT NULL AUTO_INCREMENT,
  `user_id`         int(11)            DEFAULT NULL                  COMMENT '用户id',
  `role_id`         int(11)            DEFAULT NULL                  COMMENT '角色id',
  `order_no`        int(11)            DEFAULT NULL                  COMMENT '编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  COMMENT '角色用户映射表';

-- ----------------------------
-- Records of p_user_role
-- ----------------------------
INSERT INTO `p_user_role` VALUES ('1', '1', '1', '1');
INSERT INTO `p_user_role` VALUES ('2', '2', '2', '2');
INSERT INTO `p_user_role` VALUES ('3', '3', '3', '3');
INSERT INTO `p_user_role` VALUES ('4', '1', '2', '4');
