-- 测试用户数据
INSERT INTO users (user_id, user_name, password, salt, real_name, phone, email, status, create_time, update_time)
VALUES (1, 'testuser', '123456', 'salt', '测试用户', '13800138000', 'test@example.com', 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);

-- 测试周计划数据
INSERT INTO spwx_plan_weeks (week_id, user_id, title, begin_date, end_date, content, remarks, summary, status, year, week, create_date, update_date)
VALUES 
(1, 1, '第一周计划', UNIX_TIMESTAMP()*1000 - 7*24*3600*1000, UNIX_TIMESTAMP()*1000, '完成项目A', '备注1', '总结1', 2, 2026, 18, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000),
(2, 1, '第二周计划', UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000 + 7*24*3600*1000, '完成项目B', '备注2', NULL, 1, 2026, 19, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000),
(3, 1, '第三周计划', UNIX_TIMESTAMP()*1000 + 14*24*3600*1000, UNIX_TIMESTAMP()*1000 + 21*24*3600*1000, '完成项目C', '备注3', NULL, 0, 2026, 20, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);

-- 测试角色数据
INSERT INTO sys_roles (role_id, role_name, role_key, description, status, create_time)
VALUES 
(1, '管理员', 'ADMIN', '拥有所有权限', 1, UNIX_TIMESTAMP()*1000),
(2, '普通用户', 'USER', '基本权限', 1, UNIX_TIMESTAMP()*1000);
