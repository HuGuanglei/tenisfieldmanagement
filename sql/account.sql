/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 13/08/2018 13:32:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acc_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bal` int(11) NOT NULL DEFAULT 0,
  `lock_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0：正常 1：锁定',
  `open_time` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `close_time` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0：正常 1：未激活 2：销户',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
