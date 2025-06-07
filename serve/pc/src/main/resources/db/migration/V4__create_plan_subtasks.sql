-- 周计划子任务表
CREATE TABLE IF NOT EXISTS `spwx_plan_subtasks` (
  `subtask_id` INT NOT NULL AUTO_INCREMENT COMMENT '子任务ID',
  `week_id` INT NOT NULL COMMENT '周计划ID',
  `user_id` INT NOT NULL COMMENT '用户ID',
  `title` VARCHAR(255) NOT NULL COMMENT '子任务标题',
  `description` TEXT COMMENT '子任务描述',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态: 0-待完成 1-进行中 2-已完成',
  `progress` INT DEFAULT 0 COMMENT '进度百分比',
  `create_date` BIGINT COMMENT '创建时间',
  `update_date` BIGINT COMMENT '更新时间',
  PRIMARY KEY (`subtask_id`),
  INDEX `idx_week_id` (`week_id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='周计划子任务表';
