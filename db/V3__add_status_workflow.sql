-- =====================================================
-- T017: 计划状态流转优化
-- =====================================================

-- 修改状态字段类型，支持8种状态
ALTER TABLE `week_plan`
MODIFY COLUMN `status` VARCHAR(20) NOT NULL DEFAULT 'DRAFT'
COMMENT '状态: DRAFT-草稿, PENDING_APPROVAL-待审批, IN_PROGRESS-进行中, PAUSED-已暂停, COMPLETED-已完成, CANCELLED-已取消, REJECTED-已拒绝, ARCHIVED-已归档';

-- 新增状态历史字段
ALTER TABLE `week_plan`
ADD COLUMN `status_history` JSON DEFAULT NULL
COMMENT '状态变更历史';
