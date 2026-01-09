package com.github.linfeng.app.repository;

import com.github.linfeng.app.entity.PlanProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanProgressRepository extends JpaRepository<PlanProgress, Long> {

    List<PlanProgress> findByPlanIdOrderByCreatedAtDesc(Long planId);
}
