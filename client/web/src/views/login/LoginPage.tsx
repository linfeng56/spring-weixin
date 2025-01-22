import React, { useState } from 'react';
import { Button, Input, message } from 'antd';
import axios from 'axios';

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    setLoading(true);
    try {
      const resp = await axios.post('/api/login', { username, password });
      const token = resp.data.token;
      localStorage.setItem('authToken', token);
      message.success('登录成功');
      // TODO: 跳转到首页或之前页面
    } catch (err) {
      message.error('登录失败，请检查用户名或密码');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 300, margin: '100px auto' }}>
      <h2>登录</h2>
      <Input placeholder="用户名" value={username} onChange={e => setUsername(e.target.value)} style={{ marginBottom: 12 }} />
      <Input.Password placeholder="密码" value={password} onChange={e => setPassword(e.target.value)} style={{ marginBottom: 12 }} />
      <Button type="primary" block loading={loading} onClick={handleLogin}>登录</Button>
    </div>
  );
};

export default LoginPage;
