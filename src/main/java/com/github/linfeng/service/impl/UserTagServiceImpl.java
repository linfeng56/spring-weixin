package com.github.linfeng.service.impl;

import com.github.linfeng.entity.UserTag;
import com.github.linfeng.mapper.UserTagMapper;
import com.github.linfeng.service.IUserTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户标签 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements IUserTagService {

}
