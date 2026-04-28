package com.github.linfeng.plan.mapper;

import java.util.List;
import com.github.linfeng.plan.entity.PlanTemplate;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 计划模板Mapper接口
 *
 * @author 黄麟峰
 */
@Repository
public interface PlanTemplateMapper {

    List<PlanTemplate> list();

    List<PlanTemplate> listVisible();

    List<PlanTemplate> listByCategory(@Param("category") String category);

    List<PlanTemplate> listByUserId(@Param("userId") Integer userId);

    List<PlanTemplate> searchTemplates(@Param("keyword") String keyword);

    PlanTemplate getById(@Param("id") Long id);

    Long add(@Param("template") PlanTemplate template);

    Integer update(@Param("id") Long id, @Param("template") PlanTemplate template);

    Integer deleteById(@Param("id") Long id);

    Integer incrementUseCount(@Param("id") Long id);

    Integer count();
}
