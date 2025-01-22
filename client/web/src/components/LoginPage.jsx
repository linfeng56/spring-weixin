import React, { useState } from 'react';
import axios from 'axios';

/**
 * 登录页面组件（React 实现）
 * - 输入用户名、密码
 * - 调用后端 /api/login 接口获取 JWT
 * - 成功后把 token 保存到 localStorage
 */
export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const response = await axios.post('/api/login', { username, password });
      const { token } = response.data;
      if (token) {
        localStorage.setItem('jwt_token', token);
        // 简单跳转到主页
        window.location.href = '/';
      } else {
        setError('登录失败：未返回 token');
      }
    } catch (err) {
      setError(err.response?.data?.error || '网络错误');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: '0 auto', padding: '2rem' }}>
      <h2>登录</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '1rem' }}>
          <label>用户名：</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={{ width: '100%' }}
          />
        </div>
        <div style={{ marginBottom: '1rem' }}>
          <label>密码：</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{ width: '100%' }}
          />
        </div>
        {error && <div style={{ color: 'red', marginBottom: '1rem' }}>{error}</div>}
        <button type="submit" disabled={loading} style={{ width: '100%' }}>
          {loading ? '登录中...' : '登录'}
        </button>
      </form>
    </div>
  );
}
