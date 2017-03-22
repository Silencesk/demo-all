/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50714
 Source Host           : localhost
 Source Database       : mybatis-plus

 Target Server Version : 50714
 File Encoding         : utf-8

 Date: 09/22/2016 18:55:09 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `test_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `test_type` int(11) DEFAULT '0' COMMENT '测试下划线字段命名类型',
  `role` bigint(20) DEFAULT NULL,
  `phone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=778075661690191875 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

DROP TABLE IF EXISTS `role`;
CREATE TABLE role
(
    id BIGINT(20) PRIMARY KEY NOT NULL COMMENT 'ID' AUTO_INCREMENT,
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    enable_flag VARCHAR(10),
    remarks VARCHAR(255) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色列表';

DROP TABLE IF EXISTS `data_privilege`;
CREATE TABLE data_privilege
(
    id BIGINT(20) PRIMARY KEY NOT NULL COMMENT 'ID' AUTO_INCREMENT,
    privilege_name VARCHAR(30) NOT NULL COMMENT '数据权限名称',
    privilege_sql VARCHAR(255) NOT NULL COMMENT '取权限的SQL语句',
    status VARCHAR(20) COMMENT '状态()',
    creator VARCHAR(64) NOT NULL COMMENT '建档人',
    create_time DATETIME NOT NULL COMMENT '建档时间',
    modifier VARCHAR(64) COMMENT '修改人',
    modify_time DATETIME COMMENT '修改时间',
    remarks VARCHAR(255) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限类别';

DROP TABLE IF EXISTS `data_privilege_dtl`;
CREATE TABLE data_privilege_dtl
(
    id BIGINT(20) PRIMARY KEY NOT NULL COMMENT 'ID' AUTO_INCREMENT,
    privilege_id BIGINT(20) NOT NULL COMMENT '数据权限id',
    res_code VARCHAR(16) NOT NULL COMMENT '模块编码',
    table_name VARCHAR(30) NOT NULL COMMENT '表名',
    column_name VARCHAR(30) NOT NULL COMMENT '列名',
    status VARCHAR(20) COMMENT '状态()',
    creator VARCHAR(64) NOT NULL COMMENT '建档人',
    create_time DATETIME NOT NULL COMMENT '建档时间',
    modifier VARCHAR(64) COMMENT '修改人',
    modify_time DATETIME COMMENT '修改时间',
    remarks VARCHAR(255) COMMENT '备注'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限设置';
drop index uk_data_privilege_dtl;
CREATE UNIQUE INDEX uk_data_privilege_dtl ON data_privilege_dtl (privilege_id, res_code, table_name, column_name);

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  test data
-- ----------------------------
INSERT INTO user (test_id, name, age, test_type, role, phone, dept_no) VALUES (99, 'lt', 99, 0, null, null, 'D01');
INSERT INTO data_privilege (id, privilege_name, privilege_sql, status, creator, create_time, modifier, modify_time, remarks) VALUES (1, '部门权限', 'select dept_no from user_department where user_id=[userid]', '1', 'lt', '2017-03-20 09:51:28', 'NULL', null, 'NULL');
INSERT INTO data_privilege_dtl (id, privilege_id, res_code, table_name, column_name, status, creator, create_time, modifier, modify_time, remarks) VALUES (1, 1, '110110', 'user', 'dept_no', '1', 'A', '2016-11-01 08:55:25', null, null, null);


