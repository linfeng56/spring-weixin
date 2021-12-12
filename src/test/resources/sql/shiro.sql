/*
 Source Server Type    : MySQL
 Source Server Version : 80027

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001
*/

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES (2, 'admin', 'plan:create');
INSERT INTO `roles_permissions` VALUES (5, 'admin', 'plan:delete');
INSERT INTO `roles_permissions` VALUES (4, 'admin', 'plan:update');
INSERT INTO `roles_permissions` VALUES (3, 'admin', 'plan:view');
INSERT INTO `roles_permissions` VALUES (1, 'admin', 'system:update');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `users`(username,password) VALUES ('foo', '123456');

SET FOREIGN_KEY_CHECKS = 1;
