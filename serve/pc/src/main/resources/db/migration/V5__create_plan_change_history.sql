-- 周计划变更历史表
CREATE TABLE IF NOT EXISTS `spwx_plan_change_history` (
  `change_id` INT NOT NULL AUTO_INCREMENT COMMENT '变更ID',
  `week_id` INT NOT NULL COMMENT '周计划ID',
  `user_id` INT NOT NULL COMMENT '用户ID',
  `field_name` VARCHAR(50) NOT NULL COMMENT '变更字段',
  `old_value` TEXT COMMENT '旧值',
  `new_value` TEXT COMMENT '新值',
  `change_reason` VARCHAR(500) COMMENT '变更原因',
  `create_date` BIGINT COMMENT '变更时间',
  PRIMARY KEY (`change_id`),
  INDEX `idx_week_id` (`week_id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='周计划变更历史表';
