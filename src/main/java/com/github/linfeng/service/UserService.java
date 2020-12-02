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

    /**
     * 更新用户的Token
     *
     * @param openid       微信的openid
     * @param accessToken  微信的access token
     * @param expiresIn    过期时间
     * @param refreshToken 更新时的token
     * @param scope        scope
     * @return true更新成功, false更新失败
     */
    public boolean UpdateUserToken(String openid, String accessToken, String expiresIn, String refreshToken,
        String scope) {
        return false;
    }
}
