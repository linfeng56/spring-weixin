package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.view.LoginUser;

/**
 * 服务类.
 *
 * @author 黄麟峰
 */
public interface IPlanUsersService {

    List<PlanUsers> list();

    PlanUsers getById(Integer id);

    /**
     * 用户登录验证
     *
     * @param loginName 登录名
     * @param loginPwd  登录密码
     * @return true验证通过, false验证不通过.
     */
    LoginUser checkLogin(String loginName, String loginPwd);
}
