package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.entity.PlanItems;

/**
 * 服务类.
 *
 * @author 黄麟峰
 */
public interface IPlanItemsService {

    List<PlanItems> list();

    PlanItems getById(Integer id);

    Integer add(PlanItems item);
}