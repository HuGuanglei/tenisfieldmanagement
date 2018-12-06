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

 Date: 13/08/2018 13:32:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_flow
-- ----------------------------
DROP TABLE IF EXISTS `account_flow`;
CREATE TABLE `account_flow`  (
  `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `trans_date` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trans_time` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trans_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trans_accid` int(11) NULL DEFAULT NULL COMMENT '交易账户id',
  `opp_accid` int(11) NULL DEFAULT NULL COMMENT '对方账户id',
  `trans_amt` int(11) NULL DEFAULT NULL,
  `opp_amt` int(11) NULL DEFAULT NULL,
  `trans_bal` int(11) NULL DEFAULT NULL,
  `trans_flow` int(11) NULL DEFAULT NULL,
  `dir` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借贷标志 0：入账 1：出账',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
