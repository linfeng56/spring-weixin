package com.github.linfeng.dao;

import com.github.linfeng.model.User;
import org.springframework.stereotype.Component;

/**
 * 用户基本信息操作
 *
 * @author 黄麟峰
 * @date 2020-11-23 21:18
 */
@Component
public interface UserDao {

    /**
     * 通过用户编号查询用户
     *
     * @param id 用户编号
     * @return 用户基本信息
     */
    User selectUserById(Integer id);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户基本信息
     */
    User selectUserByUserName(String userName);
}
