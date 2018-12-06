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

 Date: 13/08/2018 13:32:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_trans
-- ----------------------------
DROP TABLE IF EXISTS `account_trans`;
CREATE TABLE `account_trans`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trans_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易类型',
  `trans_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易名称',
  `flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0: 正常 1: 无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_trans
-- ----------------------------
INSERT INTO `account_trans` VALUES (1, '1001', '充值', '0');
INSERT INTO `account_trans` VALUES (2, '1002', '消费', '0');
INSERT INTO `account_trans` VALUES (3, '1003', '转账', '0');
INSERT INTO `account_trans` VALUES (4, '1004', '销户', '0');
INSERT INTO `account_trans` VALUES (5, '1005', '撤销', '0');

SET FOREIGN_KEY_CHECKS = 1;
