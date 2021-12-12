package com.github.linfeng.plan.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro登录测试
 */
public class LoginTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginTest.class);

    /**
     * 测试用户名密码登录
     */
    @Test
    public void testIniLogin() {
        // 使用ini配置文件初始化安全管理器工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:users.ini");
        // 获取安全管理器实例并设置到SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 获取subject
        Subject subject = SecurityUtils.getSubject();
        // 通过用户名密码创建身份验证token(用户身份/凭证)
        UsernamePasswordToken token = new UsernamePasswordToken("aaa", "111111");
        try {
            // 登录,身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            // 登录失身,验证失败
            LOGGER.warn(e.getMessage(), e);
        }
        // 断言用户登录成功
        Assert.assertTrue(subject.isAuthenticated());
        // 用户登出
        subject.logout();
    }

    /**
     * 测试用户名密码登录,JDBC
     */
    @Test
    public void testJdbcLogin() {
        Factory<SecurityManager> factory =
            new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("foo", "123456");
        try {
            // 登录
            subject.login(token);
        } catch (UnknownAccountException notUserEx) {
            LOGGER.warn("用户不存在.", notUserEx);
        } catch (IncorrectCredentialsException errorPasswordEx) {
            LOGGER.warn("密码不正确.", errorPasswordEx);
        } catch (AuthenticationException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        // 验证登录
        Assert.assertTrue(subject.isAuthenticated());
        try {
            // 验证角色
            Assert.assertTrue(subject.hasRole("admin"));
            // 验证权限
            Assert.assertTrue(subject.isPermitted("plan:create"));
        } finally {
            // 登出
            subject.logout();
        }

    }

    @After
    public void tearDown() {
        ThreadContext.unbindSubject();
        ThreadContext.unbindSecurityManager();
        LOGGER.warn("tear Down");
    }

}
