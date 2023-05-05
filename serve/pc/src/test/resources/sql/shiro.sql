
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_roles_permissions`(`role_name`, `permission`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES (2, 'admin', 'plan:create');
INSERT INTO `roles_permissions` VALUES (5, 'admin', 'plan:delete');
INSERT INTO `roles_permissions` VALUES (4, 'admin', 'plan:update');
INSERT INTO `roles_permissions` VALUES (3, 'admin', 'plan:view');
INSERT INTO `roles_permissions` VALUES (1, 'admin', 'system:update');

-- ----------------------------
-- Table structure for spwx_plan_items
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_items`;
CREATE TABLE `spwx_plan_items`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `week_id` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '周计划编号',
  `user_id` int(0) UNSIGNED NOT NULL COMMENT '用户编号',
  `job_type` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型:1需求,2任务,3bug,4文档,5其他',
  `job_num` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '工作编号',
  `title` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工作名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作内容',
  `begin_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '开始日期',
  `end_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '完成日期',
  `job_finish_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '整体完成日期',
  `remarks` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `create_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  `edit_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最后编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of spwx_plan_items
-- ----------------------------

-- ----------------------------
-- Table structure for spwx_plan_users
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_users`;
CREATE TABLE `spwx_plan_users`  (
  `user_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户登录名',
  `login_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录密码',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `create_date` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  `login_date` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '登录时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of spwx_plan_users
-- ----------------------------

-- ----------------------------
-- Table structure for spwx_plan_weeks
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_weeks`;
CREATE TABLE `spwx_plan_weeks`  (
  `week_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户编号',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `begin_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '开始日期',
  `end_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '截止日期',
  `create_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  `summary` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '总结',
  `summary_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '总结时间',
  PRIMARY KEY (`week_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of spwx_plan_weeks
-- ----------------------------

-- ----------------------------
-- Table structure for spwx_plan_weeks_summary
-- ----------------------------
DROP TABLE IF EXISTS `spwx_plan_weeks_summary`;
CREATE TABLE `spwx_plan_weeks_summary`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '操作编号',
  `week_id` int(0) UNSIGNED NOT NULL COMMENT '周计划编号',
  `user_id` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户编号',
  `summary` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '总结内容',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_date` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `week_user`(`week_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_tags
-- ----------------------------
DROP TABLE IF EXISTS `spwx_tags`;
CREATE TABLE `spwx_tags`  (
  `tag_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `rel_num` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '引用次数',
  `create_time` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_user_tag
-- ----------------------------
DROP TABLE IF EXISTS `spwx_user_tag`;
CREATE TABLE `spwx_user_tag`  (
  `uid` int(0) UNSIGNED NOT NULL COMMENT '用户编号',
  `tag_id` int(0) UNSIGNED NOT NULL COMMENT '标签编号',
  `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `bind_time` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '贴标时间',
  PRIMARY KEY (`uid`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for spwx_users
-- ----------------------------
DROP TABLE IF EXISTS `spwx_users`;
CREATE TABLE `spwx_users`  (
  `uid` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `scores` decimal(18, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '积分',
  `openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信OpenId',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信Access Token',
  `access_token_expire` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '微信Access Token过期时间',
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信Refresh Token',
  `wechat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信号',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `birthday` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '出生日期(年月日)',
  `sex` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别(1男2女)',
  `stature` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '身高(CM)',
  `weight` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '体重(KG)',
  `domicile` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '现居',
  `native_place` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '籍贯',
  `marital_status` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '婚姻状况(1未2已3离4丧)',
  `education` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学历',
  `profession` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '职业',
  `narrate` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '自我简述',
  `family` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '家庭背景',
  `interest` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '兴趣爱好',
  `want_style` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '理想另一半',
  `why_single` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '为何单着',
  `yearning_life` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '理想生活',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of spwx_users
-- ----------------------------
INSERT INTO `spwx_users` VALUES (1, 'abc', '', '', 0.00, '', '', 0, '', '', '', 0, 0, 0, 0, '', '', 0, '', '', '', '', '', '', '', '');
INSERT INTO `spwx_users` VALUES (2, 'hello', 'hello world', '', 0.00, '', '', 0, '', '', '', 0, 0, 0, 0, '', '', 0, '', '', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `available` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_permissions_permission`(`permission`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `available` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_roles_role`(`role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES (1, 'supperadmin', '超级管理员', 1);
INSERT INTO `sys_roles` VALUES (2, 'normaladmin', '普通管理员', 1);
INSERT INTO `sys_roles` VALUES (3, 'dataadmin', '数据管理员', 1);

-- ----------------------------
-- Table structure for sys_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions`  (
  `role_id` bigint(0) NOT NULL,
  `permission_id` bigint(0) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `sys_roles_permissions_fk_permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `sys_roles_permissions_fk_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `sys_permissions` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_roles_permissions_fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_roles` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `locked` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_users_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES (1, 'admin@google.com', 'cc3b9133daee15a7de303b3199fdd6f8', 'admin@google.com111111', 0);
INSERT INTO `sys_users` VALUES (2, 'ls', '123456', '111111', 0);

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`  (
  `user_id` bigint(0) NOT NULL,
  `role_id` bigint(0) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FK_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `FK_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_roles` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES (1, 1);
INSERT INTO `sys_users_roles` VALUES (1, 2);
INSERT INTO `sys_users_roles` VALUES (1, 3);

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_roles`(`username`, `role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (1, 'foo', 'admin');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password_salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_users_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'foo', '123456', NULL);

SET FOREIGN_KEY_CHECKS = 1;
