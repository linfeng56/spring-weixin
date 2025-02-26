-- 用户表添加头像和邮箱字段
ALTER TABLE `spwx_users` ADD COLUMN `avatar` varchar(255) DEFAULT '' COMMENT '头像URL' AFTER `yearning_life`;
ALTER TABLE `spwx_users` ADD COLUMN `email` varchar(100) DEFAULT '' COMMENT '邮箱' AFTER `avatar`;
ALTER TABLE `spwx_users` ADD UNIQUE INDEX `idx_users_email`(`email`);
