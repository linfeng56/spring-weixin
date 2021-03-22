package com.github.linfeng.service.impl;

import java.util.List;
import com.github.linfeng.entity.Users;
import com.github.linfeng.mapper.UsersMapper;
import com.github.linfeng.service.IUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class UsersServiceImpl implements IUsersService {

    private UsersMapper usersMapper;

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper){
        this.usersMapper=usersMapper;
    }
    @Override
    public List<Users> list() {
        return usersMapper.list();
    }

    @Override
    public Users getById(Integer id) {
        return usersMapper.getById(id);
    }
}
