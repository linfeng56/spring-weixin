import React, { useState } from 'react';
import { Progress, Modal, Slider, Input, message } from 'antd';
import { progressApi } from '/@/api/plan/progress';

interface ProgressBarProps {
  planId: number;
  currentProgress: number;
  onUpdate?: () => void;
}

export const ProgressBar: React.FC<ProgressBarProps> = ({ planId, currentProgress, onUpdate }) => {
  const [visible, setVisible] = useState(false);
  const [progress, setProgress] = useState(currentProgress);
  const [remark, setRemark] = useState('');
  const [loading, setLoading] = useState(false);

  const handleClick = () => {
    setProgress(currentProgress);
    setRemark('');
    setVisible(true);
  };

  const handleSubmit = async () => {
    setLoading(true);
    try {
      await progressApi.updateProgress(planId, { progress, remark });
      message.success('进度更新成功');
      setVisible(false);
      onUpdate?.();
    } catch (error) {
      message.error('进度更新失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Progress
        percent={currentProgress}
        onClick={handleClick}
        style={{ cursor: 'pointer' }}
      />
      <Modal
        title="更新进度"
        open={visible}
        onOk={handleSubmit}
        onCancel={() => setVisible(false)}
        confirmLoading={loading}
      >
        <div style={{ marginBottom: 16 }}>
          <Slider value={progress} onChange={setProgress} min={0} max={100} />
          <div style={{ textAlign: 'center', marginTop: 8 }}>{progress}%</div>
        </div>
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
