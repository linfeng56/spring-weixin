package com.github.linfeng.plan.shiro;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml",
    "classpath:/spring/spring-dataSource.xml",
    "classpath:/spring/spring-mybatis.xml",
    "classpath:/spring/spring-shiro.xml"})
public abstract class BaseShiroTest {

}
