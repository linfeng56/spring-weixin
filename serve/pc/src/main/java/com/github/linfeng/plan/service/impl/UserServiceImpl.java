package com.github.linfeng.plan.service.impl;

import java.util.List;
import java.util.Set;
import com.github.linfeng.plan.entity.User;
import com.github.linfeng.plan.mapper.UserMapper;
import com.github.linfeng.plan.service.IUserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Long createUser(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        // FIXME:此实现方式需要修改
        User user = userMapper.findUser(userId);
        user.setPassword(newPassword);
        userMapper.updateUser(user);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        userMapper.correlationRoles(userId, roleIds);
    }

    @Override
    public void unCorrelationRoles(Long userId, Long... roleIds) {
        userMapper.unCorrelationRoles(userId, roleIds);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        return userMapper.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return userMapper.findPermissions(username);
    }

    @Override
    public List<User> allUsers() {
        return userMapper.allUsers();
    }

    @Override
    public Integer cnt() {
        return userMapper.cnt();
    }

    @Override
    public boolean lock(Integer id) {
        return userMapper.lock(id) > 0;
    }

    @Override
    public User findUser(Integer userId) {
        return userMapper.findUser(userId.longValue());
    }

    @Override
    public boolean unlock(Integer id) {
        return userMapper.unlock(id);
    }

    @Override
    public boolean del(Integer id) {
        return userMapper.deleteUser(id.longValue()) > 0;
    }

    @Override
    public List<User> list(String searchText) {
        return userMapper.list(searchText);
    }
}
