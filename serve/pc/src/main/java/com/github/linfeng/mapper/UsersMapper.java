package com.github.linfeng.mapper;

import java.util.List;
import com.github.linfeng.entity.Users;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 用户信息 Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
@Qualifier("miniUserMapper")
public interface UsersMapper {

    List<Users> list();

    Users getById(@Param("uid") Integer uid);
}
