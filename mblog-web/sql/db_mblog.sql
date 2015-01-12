/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50530
Source Host           : localhost:3306
Source Database       : db_mblog

Target Server Type    : MYSQL
Target Server Version : 50530
File Encoding         : 65001

Date: 2014-12-28 16:23:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT '/dist/images/ava/default.png',
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('1', null, null, null, null, '3TGCQF25BLHU9R7IQUITN0FCC5', '0', 'admin', 'admin', '/store/ava/1/0/0/100.jpg', null);
