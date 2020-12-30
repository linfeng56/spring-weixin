
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
