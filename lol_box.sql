/*
 Navicat Premium Data Transfer

 Source Server         : localMySQL
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : lol_box

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 12/06/2024 21:27:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for black_list
-- ----------------------------
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE `black_list`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `puuid` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'puuid',
  `champion_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '英雄id',
  `kill` int(0) NOT NULL COMMENT '击杀数',
  `deaths` int(0) NOT NULL COMMENT '死亡数',
  `assists` int(0) NOT NULL COMMENT '助攻数',
  `win` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '1赢，0输',
  `reason` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '拉黑原因',
  `game_id` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'gameId',
  `meet_count` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '遇见次数',
  `is_play_with_friend` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '1双排，0单排',
  `friend_puuid` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '双排者puuid',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for champion_icon
-- ----------------------------
DROP TABLE IF EXISTS `champion_icon`;
CREATE TABLE `champion_icon`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data` blob NULL COMMENT '图片',
  `img_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片id',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '英雄头像' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for hero
-- ----------------------------
DROP TABLE IF EXISTS `hero`;
CREATE TABLE `hero`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `champion_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '英雄id',
  `champion_name` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '英雄名称',
  `alias` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '英雄小名',
  `square_portrait_path` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `puuid` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'puuid',
  `game_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tag_line` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'tagLine',
  `selected_position` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '游戏位置',
  `platform_id` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '大区',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for profile_icon
-- ----------------------------
DROP TABLE IF EXISTS `profile_icon`;
CREATE TABLE `profile_icon`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data` blob NULL COMMENT '图片',
  `img_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片id',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '玩家头像' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
