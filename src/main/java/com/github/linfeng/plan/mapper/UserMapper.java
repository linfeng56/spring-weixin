package com.github.linfeng.plan.mapper;

import java.util.List;
import java.util.Set;
import com.github.linfeng.plan.entity.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    void createUser(@Param("user") final User user);

    void updateUser(@Param("user") final User user);

    int deleteUser(@Param("userId") Long userId);

    void correlationRoles(@Param("userId") Long userId, @Param("roleIds") Long... roleIds);

    void unCorrelationRoles(@Param("userId") Long userId, @Param("roleIds") Long... roleIds);

    User findUser(@Param("userId") Long userId);

    User findByUsername(@Param("username")String username);

    Set<String> findRoles(@Param("username")String username);

    Set<String> findPermissions(String username);

    List<User> allUsers();

    Integer lock(Integer id);

    boolean unlock(Integer id);

    List<User> list(String searchText);
}
