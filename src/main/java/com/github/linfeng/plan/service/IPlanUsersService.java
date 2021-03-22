package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.entity.PlanUsers;

/**
 * 服务类.
 *
 * @author 黄麟峰
 */
public interface IPlanUsersService {

    List<PlanUsers> list();

    PlanUsers getById(Integer id);
}
