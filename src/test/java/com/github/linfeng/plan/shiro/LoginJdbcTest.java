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
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro使用JDBC存储方式登录测试
 */
public class LoginJdbcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginJdbcTest.class);

    private Subject subject;

    @Before
    public void setup() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
        LOGGER.info("setup");
    }

    /**
     * 测试用户不存在
     */
    @Test
    public void testLoginNotUserFail() {
        UsernamePasswordToken token = new UsernamePasswordToken("nobody", "123456");
        try {
            // 登录
            subject.login(token);
        } catch (AuthenticationException e) {
            Assert.assertTrue(e instanceof UnknownAccountException);
        }
        // 验证登录
        Assert.assertFalse(subject.isAuthenticated());
    }

    /**
     * 测试密码不正确
     */
    @Test
    public void testLoginPasswordFail() {
        UsernamePasswordToken token = new UsernamePasswordToken("foo", "error password");
        try {
            // 登录
            subject.login(token);
        } catch (AuthenticationException e) {
            Assert.assertTrue(e instanceof IncorrectCredentialsException);
        }
        // 验证登录
        Assert.assertFalse(subject.isAuthenticated());
    }

    /**
     * 测试角色
     */
    @Test
    public void testNotRoleLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken("foo", "123456");
        try {
            // 登录
            subject.login(token);
        } catch (AuthenticationException e) {
            LOGGER.warn(e.getMessage(), e);
            Assert.fail("登录不成功,无法进行后续测试.");
        }
        // 验证登录
        Assert.assertTrue(subject.isAuthenticated());
        try {
            // 验证角色
            Assert.assertFalse(subject.hasRole("supper"));
        } finally {
            // 登出
            subject.logout();
        }

    }

    /**
     * 测试角色
     */
    @Test
    public void testRoleLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken("foo", "123456");
        try {
            // 登录
            subject.login(token);
        } catch (AuthenticationException e) {
            LOGGER.warn(e.getMessage(), e);
            Assert.fail("登录不成功,无法进行后续测试.");
        }
        // 验证登录
        Assert.assertTrue(subject.isAuthenticated());
        try {
            // 验证角色
            Assert.assertTrue(subject.hasRole("admin"));
        } finally {
            // 登出
            subject.logout();
        }

    }

    /**
     * 测试用户登录
     */
    @Test
    public void testMainLogin() {
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
