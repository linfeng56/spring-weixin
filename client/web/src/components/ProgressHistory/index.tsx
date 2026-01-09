import React, { useEffect, useState } from 'react';
import { Timeline, Spin } from 'antd';
import { progressApi, ProgressHistoryItem } from '/@/api/plan/progress';

interface ProgressHistoryProps {
  planId: number;
}

export const ProgressHistory: React.FC<ProgressHistoryProps> = ({ planId }) => {
  const [history, setHistory] = useState<ProgressHistoryItem[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadHistory();
  }, [planId]);

  const loadHistory = async () => {
    setLoading(true);
    try {
      const data = await progressApi.getProgressHistory(planId);
      setHistory(data);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Spin />;
  }

  return (
    <Timeline>
      {history.map((item) => (
        <Timeline.Item key={item.id}>
          <div>
            <strong>{item.progress}%</strong>
            {item.remark && <div style={{ color: '#666' }}>{item.remark}</div>}
            <div style={{ fontSize: 12, color: '#999' }}>{item.createdAt}</div>
          </div>
        </Timeline.Item>
      ))}
    </Timeline>
  );
};
