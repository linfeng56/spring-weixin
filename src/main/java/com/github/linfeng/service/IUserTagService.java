package com.github.linfeng.service;

import java.util.List;
import com.github.linfeng.entity.UserTag;

/**
 * 用户标签 服务类.
 *
 * @author 黄麟峰
 */
public interface IUserTagService {

    List<UserTag> list();

    UserTag getById(Integer id);
}
