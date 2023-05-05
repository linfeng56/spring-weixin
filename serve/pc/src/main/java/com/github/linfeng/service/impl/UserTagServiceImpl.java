package com.github.linfeng.service.impl;

import java.util.List;
import com.github.linfeng.entity.UserTag;
import com.github.linfeng.mapper.UserTagMapper;
import com.github.linfeng.service.IUserTagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户标签 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class UserTagServiceImpl implements IUserTagService {

    @Autowired
    private UserTagMapper userTagMapper;

    @Override
    public List<UserTag> list() {
        return userTagMapper.list();
    }

    @Override
    public UserTag getById(Integer id) {
        return userTagMapper.getById(id);
    }
}
