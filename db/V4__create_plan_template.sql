-- 计划模板管理表
-- 用于存储周计划模板，支持系统预设和用户自定义模板
CREATE TABLE IF NOT EXISTS plan_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    name VARCHAR(100) NOT NULL COMMENT '模板名称',
    description VARCHAR(500) COMMENT '模板描述',
    category VARCHAR(50) NOT NULL COMMENT '模板分类：WORK/STUDY/LIFE/OTHER',
    content TEXT NOT NULL COMMENT '模板内容(JSON格式)',
    is_system BOOLEAN DEFAULT FALSE COMMENT '是否系统模板',
    is_public BOOLEAN DEFAULT FALSE COMMENT '是否公开(用户可见)',
    user_id BIGINT COMMENT '创建用户ID',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category (category),
    INDEX idx_user_id (user_id),
    INDEX idx_is_public (is_public),
    INDEX idx_use_count (use_count DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划模板表';

-- 初始化系统预设模板
INSERT INTO plan_template (name, description, category, content, is_system, is_public, user_id, use_count) VALUES
('周工作计划', '适用于周度工作计划编写', 'WORK', 
'{"title":"第{{week}}周工作计划","content":"本周重点工作：\n1. \n2. \n3. \n\n需协调事项：\n- \n\n风险评估：\n- ","priority":"HIGH","tags":["工作","周计划"]}', 
TRUE, TRUE, NULL, 0),
('项目开发计划', '适用于项目开发任务规划', 'WORK', 
'{"title":"{{projectName}}开发计划","content":"项目背景：\n{{projectDesc}}\n\n开发目标：\n1. \n2. \n3. \n\n技术方案：\n- \n\n里程碑：\n- 阶段1：\n- 阶段2：\n- 阶段3：\n\n风险点：\n- ","priority":"HIGH","tags":["工作","项目"]}', 
TRUE, TRUE, NULL, 0),
('学习计划', '适用于个人学习规划', 'STUDY', 
'{"title":"{{subject}}学习计划","content":"学习目标：\n1. \n2. \n\n学习内容：\n- 第1周：\n- 第2周：\n- 第3周：\n- 第4周：\n\n资源准备：\n- \n\n预期成果：\n- ","priority":"MEDIUM","tags":["学习","个人成长"]}', 
TRUE, TRUE, NULL, 0),
('周总结', '适用于周度工作总结', 'WORK', 
'{"title":"第{{week}}周工作总结","content":"本周完成：\n1. \n2. \n3. \n\n本周问题：\n- \n\n下周计划：\n1. \n2. \n3. \n\n心得体会：\n- ","priority":"MEDIUM","tags":["工作","总结"]}', 
TRUE, TRUE, NULL, 0),
('会议纪要', '适用于会议内容记录', 'OTHER', 
'{"title":"{{meetingName}}会议纪要","content":"会议时间：{{date}}\n会议地点：\n参会人员：\n\n议题1：\n- 讨论内容：\n- 决议：\n\n议题2：\n- 讨论内容：\n- 决议：\n\n待跟进事项：\n1. \n2. \n","priority":"LOW","tags":["工作","会议"]}', 
TRUE, TRUE, NULL, 0);
