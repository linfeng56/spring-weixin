package com.github.linfeng.plan.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.mapper.PlanUsersMapper;
import com.github.linfeng.plan.service.IPlanUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class PlanUsersServiceImpl implements IPlanUsersService {

    @Autowired
    private PlanUsersMapper planUsersMapper;

    @Override
    public List<PlanUsers> list() {
        return planUsersMapper.list();
    }

    @Override
    public PlanUsers getById(Integer id) {
        return planUsersMapper.getById(id);
    }

    @Override
    public boolean checkLogin(String loginName, String loginPwd) {
        boolean ret = false;

        if (StringUtils.hasText(loginName) && StringUtils.hasText(loginPwd)) {
            PlanUsers planUser = planUsersMapper.getUserByLoginName(loginName);
            if (planUser != null
                && encodePassword(loginPwd).equals(planUser.getLoginPassword())) {
                Long time = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                boolean loginTime = planUsersMapper.updateLoginDate(planUser.getUserId(), time);
                ret = true;
            }
        }
        return ret;
    }

    /**
     * TODO:密码加密算法
     *
     * @param password 源密码
     * @return 已加密密码
     */
    protected String encodePassword(String password) {
        return password;
    }
}
