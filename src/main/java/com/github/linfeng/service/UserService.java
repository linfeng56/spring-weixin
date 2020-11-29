package com.github.linfeng.service;

import com.github.linfeng.dao.UserDao;
import com.github.linfeng.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务类
 *
 * @author 黄麟峰
 * @date 2020-11-26 17:36
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.selectAllUser();
    }

    /**
     * 获取用户基本信息
     *
     * @param id 用户编号
     * @return 用户基本信息
     */
    public User getUser(Integer id) {
        return userDao.selectUserById(id);
    }
}
