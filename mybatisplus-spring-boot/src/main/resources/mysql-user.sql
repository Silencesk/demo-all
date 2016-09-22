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
--  Table structure for `user`
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

SET FOREIGN_KEY_CHECKS = 1;
