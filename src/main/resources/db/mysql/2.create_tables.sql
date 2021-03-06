
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for spwx_plan_items
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_items`;
CREATE TABLE `spwx_plan_items`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `week_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '周计划编号',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户编号',
  `job_type` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型:1需求,2任务,3bug,4文档,5其他',
  `job_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '工作编号',
  `title` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工作名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作内容',
  `begin_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '开始日期',
  `end_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '完成日期',
  `job_finish_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '整体完成日期',
  `remarks` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `create_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  `edit_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最后编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_plan_users
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_users`;
CREATE TABLE `spwx_plan_users`  (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户登录名',
  `login_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录密码',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `create_date` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  `login_date` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '登录时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_plan_weeks
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_weeks`;
CREATE TABLE `spwx_plan_weeks`  (
  `week_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户编号',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `begin_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '开始日期',
  `end_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '截止日期',
  `create_date` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`week_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_tags
-- ----------------------------
DROP TABLE IF EXISTS `spwx_tags`;
CREATE TABLE `spwx_tags`  (
  `tag_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `rel_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '引用次数',
  `create_time` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_user_tag
-- ----------------------------
DROP TABLE IF EXISTS `spwx_user_tag`;
CREATE TABLE `spwx_user_tag`  (
  `uid` int(10) UNSIGNED NOT NULL COMMENT '用户编号',
  `tag_id` int(10) UNSIGNED NOT NULL COMMENT '标签编号',
  `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `bind_time` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '贴标时间',
  PRIMARY KEY (`uid`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_users
-- ----------------------------
DROP TABLE IF EXISTS `spwx_users`;
CREATE TABLE `spwx_users`  (
  `uid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `scores` decimal(18, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '积分',
  `openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信OpenId',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信Access Token',
  `access_token_expire` int(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '微信Access Token过期时间',
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信Refresh Token',
  `wechat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信号',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `birthday` int(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '出生日期(年月日)',
  `sex` int(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别(1男2女)',
  `stature` int(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '身高(CM)',
  `weight` int(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '体重(KG)',
  `domicile` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '现居',
  `native_place` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '籍贯',
  `marital_status` int(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '婚姻状况(1未2已3离4丧)',
  `education` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学历',
  `profession` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '职业',
  `narrate` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '自我简述',
  `family` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '家庭背景',
  `interest` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '兴趣爱好',
  `want_style` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '理想另一半',
  `why_single` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '为何单着',
  `yearning_life` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '理想生活',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
