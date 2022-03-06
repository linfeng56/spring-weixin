package com.github.linfeng.plan.mapper;

import java.util.Set;
import com.github.linfeng.plan.entity.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    void createUser(@Param("user") final User user);

    void updateUser(@Param("user") final User user);

    void deleteUser(@Param("userId") Long userId);

    void correlationRoles(@Param("userId") Long userId, @Param("roleIds") Long... roleIds);

    void unCorrelationRoles(@Param("userId") Long userId, @Param("roleIds") Long... roleIds);

    User findUser(@Param("userId") Long userId);

    User findByUsername(@Param("username")String username);

    Set<String> findRoles(@Param("username")String username);

    Set<String> findPermissions(String username);
}
