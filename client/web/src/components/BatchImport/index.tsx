import React, { useState } from 'react';
import { Upload, Button, Modal, Table, message } from 'antml:parameter>
import { UploadOutlined, DownloadOutlined } from '@ant-design/icons';
import { batchApi, BatchImportResult } from '/@/api/plan/batch';

export const BatchImport: React.FC<{ onSuccess?: () => void }> = ({ onSuccess }) => {
  const [visible, setVisible] = useState(false);
  const [result, setResult] = useState<BatchImportResult | null>(null);
  const [loading, setLoading] = useState(false);

  const handleUpload = async (file: File) => {
    setLoading(true);
    try {
      const res = await batchApi.importExcel(file);
      setResult(res);
      if (res.failed === 0) {
        message.success(`成功导入${res.success}条记录`);
        setVisible(false);
        onSuccess?.();
      }
    } catch (error) {
      message.error('导入失败');
    } finally {
      setLoading(false);
    }
    return false;
  };

  const columns = [
    { title: '行号', dataIndex: 'row', key: 'row' },
    { title: '字段', dataIndex: 'field', key: 'field' },
    { title: '错误信息', dataIndex: 'message', key: 'message' },
  ];

  return (
    <>
      <Button onClick={() => setVisible(true)}>批量导入</Button>
      <Modal
        title="批量导入"
        open={visible}
        onCancel={() => setVisible(false)}
        footer={null}
        width={600}
      >
        <div style={{ marginBottom: 16 }}>
          <Button icon={<DownloadOutlined />} onClick={batchApi.downloadTemplate}>
            下载模板
          </Button>
        </div>
        <Upload beforeUpload={handleUpload} showUploadList={false}>
          <Button icon={<UploadOutlined />} loading={loading}>
            选择文件
          </Button>
        </Upload>
        {result && (
          <div style={{ marginTop: 16 }}>
            <p>总计: {result.total}, 成功: {result.success}, 失败: {result.failed}</p>
            {result.errors.length > 0 && (
              <Table
                dataSource={result.errors}
                columns={columns}
                size="small"
                pagination={false}
              />
            )}
          </div>
        )}
      </Modal>
    </>
  );
};
