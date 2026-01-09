package com.github.linfeng.app.repository;

import com.github.linfeng.app.entity.WeekPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekPlanRepository extends JpaRepository<WeekPlan, Long> {

    List<WeekPlan> findByUserIdAndYearAndWeekNumber(Long userId, Integer year, Integer weekNumber);

    List<WeekPlan> findByUserId(Long userId);
}
