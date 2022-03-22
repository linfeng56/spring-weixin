package com.github.linfeng.plan.shiro;

import java.util.Collections;
import com.alibaba.druid.pool.DruidDataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * shiro使用JDBC存储方式登录测试
 */
public class LoginJdbcJavaTest extends BaseShiroTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginJdbcJavaTest.class);

    private Subject subject;

    @Autowired
    private DruidDataSource dataSource;
    /**
     * 配置shiro
     */
    private void config(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置认证器(authenticator)
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        // 设置授权器(authorizer)
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);

        // 设置Realm
        // DruidDataSource dataSource = new DruidDataSource();
        // dataSource.setDriverClassName("org.h2.Driver");
        // dataSource.setUrl("jdbc:h2:mem:spring_weixin;MODE=MySql;DB_CLOSE_DELAY=-1");
        // dataSource.setUsername("root");
        // dataSource.setPassword("");

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealms(Collections.singletonList((Realm) jdbcRealm));

        // 将SecurityManager设置到SecurityUtils中以便全局使用
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Before
    public void setup() {
        config();
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
