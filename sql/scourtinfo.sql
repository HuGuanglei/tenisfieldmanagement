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

 Date: 01/08/2018 15:47:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for scourtinfo
-- ----------------------------
DROP TABLE IF EXISTS `scourtinfo`;
CREATE TABLE `scourtinfo`  (
  `courtId` int(11) NOT NULL,
  `courtName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createDate` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`courtId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
