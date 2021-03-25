package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeeks;

/**
 * 服务类.
 *
 * @author 黄麟峰
 */
public interface IPlanWeeksService {

    List<PlanWeeks> list();

    PlanWeeks getById(Integer id);

    Integer add(PlanWeeks weeks);
}
