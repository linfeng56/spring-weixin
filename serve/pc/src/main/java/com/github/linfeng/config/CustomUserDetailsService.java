package com.github.linfeng.config;

import com.github.linfeng.entity.Users;
import com.github.linfeng.service.IUsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUsersService usersService;

    public CustomUserDetailsService(IUsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersService.getByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return new CustomUserDetails(users);
    }
}
