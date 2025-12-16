package com.github.linfeng.service;

import java.util.List;
import com.github.linfeng.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.linfeng.utils.JwtTokenUtil;

/**
 * 用户服务类
 *
 * @author 黄麟峰
 */
@Service
@Qualifier("miniUserService")
public class UserService {

    @Autowired
    @Qualifier("miniIUserService")
    private IUsersService service;

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Users> list() {
        return service.list();
    }

    public Users getById(Integer id) {
        return service.getById(id);
    }

    /**
     * 登录并返回 JWT Token
     */
    public String login(String username, String rawPassword) {
        Users user = service.getByUsername(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPwd())) {
            return jwtTokenUtil.generateToken(username);
        }
        return null;
    }

    /**
     * 更新用户的Token
     *
     * @param openid       微信的openid
     * @param accessToken  微信的access token
     * @param expiresIn    过期时间
     * @param refreshToken 更新时的token
     * @param scope        scope
     * @return true更新成功, false更新失败
     */
    public boolean updateUserToken(String openid, String accessToken, String expiresIn, String refreshToken,
        String scope) {
        return false;
    }
}
