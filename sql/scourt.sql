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

 Date: 01/08/2018 15:47:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for scourt
-- ----------------------------
DROP TABLE IF EXISTS `scourt`;
CREATE TABLE `scourt`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bdate` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `startHour` int(11) NOT NULL,
  `endHour` int(11) NOT NULL,
  `courtId` int(11) NULL DEFAULT NULL,
  `courtName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
