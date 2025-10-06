-- 角色表
CREATE TABLE IF NOT EXISTS `sys_roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_key` VARCHAR(50) NOT NULL COMMENT '角色标识',
  `description` VARCHAR(255) COMMENT '描述',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表（菜单级）
CREATE TABLE IF NOT EXISTS `sys_permissions` (
  `perm_id` INT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `perm_name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `perm_key` VARCHAR(50) NOT NULL COMMENT '权限标识',
  `parent_id` INT DEFAULT 0 COMMENT '父权限ID',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
  PRIMARY KEY (`perm_id`),
  UNIQUE KEY `uk_perm_key` (`perm_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `sys_user_roles` (
  `user_id` INT NOT NULL COMMENT '用户ID',
  `role_id` INT NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `sys_role_perms` (
  `role_id` INT NOT NULL COMMENT '角色ID',
  `perm_id` INT NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`, `perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 插入预置角色
INSERT INTO sys_roles (role_name, role_key, description, status, create_time) VALUES
('管理员', 'ADMIN', '拥有所有权限', 1, UNIX_TIMESTAMP()*1000),
('经理', 'MANAGER', '周计划管理、汇总、导出权限', 1, UNIX_TIMESTAMP()*1000),
('普通用户', 'USER', '基本周计划管理权限', 1, UNIX_TIMESTAMP()*1000);

-- 插入预置权限（菜单级）
INSERT INTO sys_permissions (perm_name, perm_key, parent_id, sort, status) VALUES
('系统管理', 'system', 0, 100, 1),
('用户管理', 'system:user', 1, 1, 1),
('角色管理', 'system:role', 1, 2, 1),
('周计划', 'week-plan', 0, 10, 1),
('周计划管理', 'week-plan:list', 4, 1, 1),
('周计划新增', 'week-plan:add', 4, 2, 1),
('周计划编辑', 'week-plan:edit', 4, 3, 1),
('周计划删除', 'week-plan:delete', 4, 4, 1),
('周计划汇总', 'week-plan:summary', 4, 5, 1),
('周计划导出', 'week-plan:export', 4, 6, 1),
('计划日历', 'calendar', 0, 20, 1),
('关联总结', 'linked-summary', 0, 30, 1);

-- ADMIN角色拥有所有权限
INSERT INTO sys_role_perms (role_id, perm_id) 
SELECT 1, perm_id FROM sys_permissions WHERE status = 1;

-- MANAGER角色拥有周计划相关权限
INSERT INTO sys_role_perms (role_id, perm_id)
SELECT 2, perm_id FROM sys_permissions WHERE perm_key IN (
  'week-plan', 'week-plan:list', 'week-plan:add', 'week-plan:edit', 
  'week-plan:delete', 'week-plan:summary', 'week-plan:export',
  'calendar', 'linked-summary'
);

-- USER角色拥有基本权限
INSERT INTO sys_role_perms (role_id, perm_id)
SELECT 3, perm_id FROM sys_permissions WHERE perm_key IN (
  'week-plan', 'week-plan:list', 'week-plan:add', 'week-plan:edit', 'week-plan:delete'
);
