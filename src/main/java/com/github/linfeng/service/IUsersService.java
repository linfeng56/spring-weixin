package com.github.linfeng.service;

import java.util.List;
import com.github.linfeng.entity.Users;

/**
 * 用户信息 服务类.
 *
 * @author 黄麟峰
 */
public interface IUsersService {

    List<Users> list();

    Users getById(Integer id);
}
