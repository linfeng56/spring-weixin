package com.github.linfeng.plan.mapper;

import java.util.List;
import com.github.linfeng.plan.entity.PlanUsers;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
public interface PlanUsersMapper {

    List<PlanUsers> list();

    PlanUsers getById(@Param("id") Integer id);

    /**
     * 通过用户名获取用户
     *
     * @param loginName 用户名
     * @return 用户信息
     */
    PlanUsers getUserByLoginName(@Param("loginName") String loginName);

    /**
     * 更新最后登录时间
     *
     * @param userId 用户编号
     * @param time   最后登录时间
     * @return 更新是否成功
     */
    boolean updateLoginDate(@Param("userId") Integer userId, @Param("loginDate") Long time);

    Integer count();
}
