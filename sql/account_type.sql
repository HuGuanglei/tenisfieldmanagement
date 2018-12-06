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

 Date: 13/08/2018 13:32:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_type
-- ----------------------------
DROP TABLE IF EXISTS `account_type`;
CREATE TABLE `account_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acc_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `init_amt` int(11) NULL DEFAULT NULL COMMENT '初始金额',
  `valid_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0:有效 1：无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_type
-- ----------------------------
INSERT INTO `account_type` VALUES (1, '100', '个人账户', 0, '0');
INSERT INTO `account_type` VALUES (2, '301', '系统贷账户', 0, '0');
INSERT INTO `account_type` VALUES (3, '302', '系统借账户', 1000000000, '0');

SET FOREIGN_KEY_CHECKS = 1;
