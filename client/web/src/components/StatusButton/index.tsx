import React, { useState } from 'react';
import { Tag, Dropdown, Menu, Modal, Input, message } from 'antd';
import { statusApi } from '/@/api/plan/status';

interface StatusButtonProps {
  planId: number;
  currentStatus: string;
  onUpdate?: () => void;
}

const statusConfig = {
  DRAFT: {
    label: '草稿',
    color: 'default',
    actions: [
      { label: '提交审批', target: 'PENDING_APPROVAL' },
      { label: '直接开始', target: 'IN_PROGRESS' },
      { label: '取消', target: 'CANCELLED' },
    ],
  },
  PENDING_APPROVAL: {
    label: '待审批',
    color: 'warning',
    actions: [
      { label: '通过', target: 'IN_PROGRESS' },
      { label: '拒绝', target: 'REJECTED' },
      { label: '取消', target: 'CANCELLED' },
    ],
  },
  IN_PROGRESS: {
    label: '进行中',
    color: 'processing',
    actions: [
      { label: '暂停', target: 'PAUSED' },
      { label: '完成', target: 'COMPLETED' },
      { label: '取消', target: 'CANCELLED' },
    ],
  },
  PAUSED: {
    label: '已暂停',
    color: 'default',
    actions: [
      { label: '继续', target: 'IN_PROGRESS' },
      { label: '取消', target: 'CANCELLED' },
    ],
  },
  COMPLETED: {
    label: '已完成',
    color: 'success',
    actions: [{ label: '归档', target: 'ARCHIVED' }],
  },
  CANCELLED: {
    label: '已取消',
    color: 'default',
    actions: [{ label: '重新开始', target: 'DRAFT' }],
  },
  REJECTED: {
    label: '已拒绝',
    color: 'error',
    actions: [{ label: '修改后重新提交', target: 'DRAFT' }],
  },
  ARCHIVED: {
    label: '已归档',
    color: 'default',
    actions: [],
  },
};

export const StatusButton: React.FC<StatusButtonProps> = ({ planId, currentStatus, onUpdate }) => {
  const [visible, setVisible] = useState(false);
  const [remark, setRemark] = useState('');
  const [targetStatus, setTargetStatus] = useState('');
  const [loading, setLoading] = useState(false);

  const config = statusConfig[currentStatus] || statusConfig.DRAFT;

  const handleMenuClick = ({ key }) => {
    setTargetStatus(key);
    setRemark('');
    setVisible(true);
  };

  const handleSubmit = async () => {
    setLoading(true);
    try {
      await statusApi.changeStatus(planId, { status: targetStatus, remark });
      message.success('状态更新成功');
      setVisible(false);
      onUpdate?.();
    } catch (error) {
      message.error('状态更新失败');
    } finally {
      setLoading(false);
    }
  };

  const menu = (
    <Menu onClick={handleMenuClick}>
      {config.actions.map((action) => (
        <Menu.Item key={action.target}>{action.label}</Menu.Item>
      ))}
    </Menu>
  );

  return (
    <>
      <Dropdown overlay={menu} disabled={config.actions.length === 0}>
        <Tag color={config.color} style={{ cursor: 'pointer' }}>
          {config.label}
        </Tag>
      </Dropdown>
      <Modal
        title="状态流转"
        open={visible}
        onOk={handleSubmit}
        onCancel={() => setVisible(false)}
        confirmLoading={loading}
      >
        <Input.TextArea
          placeholder="备注（可选）"
          value={remark}
          onChange={(e) => setRemark(e.target.value)}
          rows={3}
        />
      </Modal>
    </>
  );
};
