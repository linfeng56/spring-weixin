package com.github.linfeng.mapper;

import java.util.List;
import com.github.linfeng.entity.UserTag;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户标签 Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
public interface UserTagMapper {

    List<UserTag> list();

    UserTag getById(@Param("id") Integer id);
}
