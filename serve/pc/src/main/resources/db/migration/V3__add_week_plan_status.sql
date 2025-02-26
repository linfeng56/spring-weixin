-- 周计划表添加状态字段
ALTER TABLE `spwx_plan_weeks` ADD COLUMN `status` tinyint(1) DEFAULT 0 COMMENT '状态: 0-待提交 1-进行中 2-已完成 3-已归档' AFTER `summary_date`;
