package com.github.linfeng.plan.service;

import java.util.List;
import java.util.Set;
import com.github.linfeng.plan.entity.User;

public interface IUserService {

    Long createUser(User user);

    void changePassword(Long userId, String newPassword);

    void correlationRoles(Long userId, Long... roleIds);

    void unCorrelationRoles(Long userId, Long... roleIds);

    User findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

    List<User> allUsers();

    Integer cnt();

    boolean lock(Integer id);

    User findUser(Integer userId);

    boolean unlock(Integer id);

    boolean del(Integer id);

    List<User> list(String searchText);
}
