import React, { useEffect, useState } from 'react';
import { Timeline, Spin } from 'antd';
import { statusApi, StatusHistoryItem } from '/@/api/plan/status';

interface StatusHistoryProps {
  planId: number;
}

export const StatusHistory: React.FC<StatusHistoryProps> = ({ planId }) => {
  const [history, setHistory] = useState<StatusHistoryItem[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadHistory();
  }, [planId]);

  const loadHistory = async () => {
    setLoading(true);
    try {
      const data = await statusApi.getStatusHistory(planId);
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
      {history.map((item, index) => (
        <Timeline.Item key={index}>
          <div>
            <strong>{item.statusLabel}</strong>
            {item.remark && <div style={{ color: '#666' }}>{item.remark}</div>}
            <div style={{ fontSize: 12, color: '#999' }}>{item.timestamp}</div>
          </div>
        </Timeline.Item>
      ))}
    </Timeline>
  );
};
