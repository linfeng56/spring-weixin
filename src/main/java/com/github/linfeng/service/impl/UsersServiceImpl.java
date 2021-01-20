package com.github.linfeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.linfeng.entity.Users;
import com.github.linfeng.mapper.UsersMapper;
import com.github.linfeng.service.IUsersService;

import org.springframework.stereotype.Service;

/**
 * 用户信息 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
