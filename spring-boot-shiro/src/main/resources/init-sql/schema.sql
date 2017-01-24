/*
Date: 2016-12-23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `p_permission`
-- ----------------------------
DROP TABLE IF EXISTS `p_permission`;
CREATE TABLE `p_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50)  NOT NULL COMMENT '权限名',
  `sn` int(2)  NOT NULL UNIQUE COMMENT '权限标识(BIT)',
  `comment` varchar(50)  DEFAULT NULL COMMENT '权限说明',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '权限表';

-- ----------------------------
-- Records of p_permission
-- ----------------------------
INSERT INTO `p_permission` VALUES ('1', 'query', 0, '查询', '2016-06-01 23:41:39');
INSERT INTO `p_permission` VALUES ('2', 'create', 1, '增加', '2016-06-01 23:41:39');
INSERT INTO `p_permission` VALUES ('3', 'update', 2, '修改', '2016-06-01 23:41:39');
INSERT INTO `p_permission` VALUES ('4', 'delete', 3, '删除', '2016-06-01 23:41:39');

-- ----------------------------
-- Table structure for `p_acl`
-- ----------------------------
DROP TABLE IF EXISTS `p_acl`;
CREATE TABLE `p_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `principal_type` varchar(50)  NOT NULL COMMENT '主体类型(角色或用户)',
  `principal_sn` int(11)  NOT NULL COMMENT '主体标识(ID)',
  `resource_sn` int(11)  NOT NULL COMMENT '资源标识(Module ID)',
  `acl_state` int(32)  NOT NULL COMMENT '授权状态(用（bit）表示, 64bit-20位max)',
  `acl_ext_state` int(2)  DEFAULT 0 COMMENT '是否继承(0否-1是)',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT '访问控制列表';

-- ----------------------------
-- Records of p_acl
-- ----------------------------
INSERT INTO `p_acl` VALUES ('1', 'ROLE', 1, 1, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('2', 'ROLE', 1, 2, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('3', 'ROLE', 1, 3, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('4', 'ROLE', 1, 4, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('5', 'ROLE', 1, 5, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('6', 'ROLE', 1, 6, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('7', 'ROLE', 1, 7, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('8', 'ROLE', 1, 8, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('9', 'ROLE', 1, 9, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('10', 'ROLE', 1, 10, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('11', 'ROLE', 1, 11, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('12', 'ROLE', 1, 12, 15, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('13', 'ROLE', 2, 1, 9, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('14', 'ROLE', 3, 1, 5, 0, '2016-06-01 23:41:39');
INSERT INTO `p_acl` VALUES ('15', 'USER', 4, 1, 1, 0, '2016-06-01 23:41:39');

-- ----------------------------
-- Table structure for `p_module`
-- ----------------------------
DROP TABLE IF EXISTS `p_module`;
CREATE TABLE `p_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(10) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  `order_no` int(10) DEFAULT NULL,
  `sn` varchar(32) NOT NULL UNIQUE,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_module
-- ----------------------------
INSERT INTO `p_module` VALUES ('1', null, 'Overview', '/index', '1', 'IndexUrl', '2016-06-01 23:41:39');
INSERT INTO `p_module` VALUES ('2', null, 'Reports', '/reports', '1', 'Reports', '2016-06-02 09:42:17');
INSERT INTO `p_module` VALUES ('3', null, 'Analytics', '/analytics', '1', 'Analytics', '2016-06-03 21:42:17');
INSERT INTO `p_module` VALUES ('4', null, 'Export', '/export', '1', 'Export', '2016-06-03 20:38:01');
INSERT INTO `p_module` VALUES ('5', null, 'Nav item', '/navitem', '1', 'Nav_item', '2016-06-03 20:38:04');
INSERT INTO `p_module` VALUES ('6', null, 'Nav item again', '/navitemagain', '1', 'Nav_item_again', '2016-06-03 20:38:08');
INSERT INTO `p_module` VALUES ('7', null, 'One more nav', '/onenav', '1', 'One_more_nav', '2016-06-21 20:38:11');
INSERT INTO `p_module` VALUES ('8', null, 'Another nav item', '/anothernav', '1', 'Another_nav_item', '2016-05-29 20:38:23');
INSERT INTO `p_module` VALUES ('9', null, 'More navigation', '/more', '1', 'More_navigation', '2016-06-05 20:38:14');
INSERT INTO `p_module` VALUES ('10', null, 'Acl Manager', '/acl', '1', 'Acl_Manager', '2016-07-01 20:38:28');
INSERT INTO `p_module` VALUES ('11', null, 'One more nav', null, '1', 'One_more_nav1', '2016-05-31 20:38:18');
INSERT INTO `p_module` VALUES ('12', null, 'Another nav item', null, '1', 'Another_nav_item1', '2016-05-29 20:38:31');

-- ----------------------------
-- Table structure for `p_role`
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT NULL UNIQUE,
  `description` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_role
-- ----------------------------
INSERT INTO `p_role` VALUES ('1', 'admin', '系统管理员', '2016-06-01 23:41:11');
INSERT INTO `p_role` VALUES ('2', 'normal', '普通人员', '2016-06-01 23:41:11');
INSERT INTO `p_role` VALUES ('3', 'manager', '管理人员', '2016-06-01 23:41:11');
-- ----------------------------
-- Table structure for `p_role_module`
-- ----------------------------
DROP TABLE IF EXISTS `p_role_module`;
CREATE TABLE `p_role_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
-- ----------------------------
-- Table structure for `p_user`
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) UNIQUE NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user
-- ----------------------------
INSERT INTO `p_user` VALUES ('1', 'admin', 'admin', 'Admin', '2016-06-01 23:35:17');
INSERT INTO `p_user` VALUES ('2', 'lance', 'admin', 'Lance', '2016-06-02 23:35:38');
INSERT INTO `p_user` VALUES ('3', 'test', 'test', 'test', '2016-06-02 23:35:38');
INSERT INTO `p_user` VALUES ('4', 'test4', 'test', 'noRole', '2016-06-02 23:35:38');
-- ----------------------------
-- Table structure for `p_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `p_user_role`;
CREATE TABLE `p_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user_role
-- ----------------------------
INSERT INTO `p_user_role` VALUES ('1', '1', '1', '1');
INSERT INTO `p_user_role` VALUES ('2', '2', '2', '2');
INSERT INTO `p_user_role` VALUES ('3', '3', '3', '3');
INSERT INTO `p_user_role` VALUES ('4', '1', '2', '4');
