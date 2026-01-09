-- =====================================================
-- 周计划核心表
-- =====================================================

-- 周计划表
CREATE TABLE IF NOT EXISTS `week_plan` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `title` VARCHAR(200) NOT NULL COMMENT '计划标题',
  `content` TEXT COMMENT '计划内容',
  `year` INT(4) NOT NULL COMMENT '年份',
  `week_number` INT(2) NOT NULL COMMENT '周数(1-53)',
  `status` VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '状态: DRAFT-草稿 IN_PROGRESS-进行中 COMPLETED-已完成 ARCHIVED-已归档',
  `current_progress` INT(3) NOT NULL DEFAULT 0 COMMENT '当前进度(0-100)',
  `priority` VARCHAR(10) DEFAULT 'MEDIUM' COMMENT '优先级: HIGH-高 MEDIUM-中 LOW-低',
  `tags` VARCHAR(500) COMMENT '标签(逗号分隔)',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_year_week` (`year`, `week_number`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='周计划表';

-- =====================================================
-- 计划进度跟踪表（T016）
-- =====================================================

-- 计划进度记录表
CREATE TABLE IF NOT EXISTS `plan_progress` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '进度记录ID',
  `plan_id` BIGINT(20) NOT NULL COMMENT '计划ID',
  `progress` INT(3) NOT NULL DEFAULT 0 COMMENT '完成百分比(0-100)',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '进度备注',
  `created_by` BIGINT(20) NOT NULL COMMENT '更新人ID',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划进度记录表';
