package com.github.linfeng.admin.service.impl;

import com.github.linfeng.admin.service.LoginService;

import org.springframework.stereotype.Service;

/**
 * 后台登录服务
 *
 * @author 黄麟峰
 * @date 2021-03-12
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public boolean checkLogin(String userName, String password) {
        // 未创建数据库,临时判断登录信息 开始
        String tmpLoginName = "admin@google.com";
        String tmpLoginPwd = "111111";
        // 未创建数据库,临时判断登录信息 结束

        if (tmpLoginName.equalsIgnoreCase(userName)
            && tmpLoginPwd.equalsIgnoreCase(password)) {
            return true;
        } else {
            return false;
        }
    }
}
