package com.github.linfeng.app.service;

import com.alibaba.excel.EasyExcel;
import com.github.linfeng.app.dto.BatchImportResult;
import com.github.linfeng.app.dto.WeekPlanExcelDTO;
import com.github.linfeng.app.entity.WeekPlan;
import com.github.linfeng.app.enums.PlanStatus;
import com.github.linfeng.app.repository.WeekPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BatchService {

    @Autowired
    private WeekPlanRepository weekPlanRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public BatchImportResult importFromExcel(MultipartFile file, Long userId) throws IOException {
        BatchImportResult result = new BatchImportResult();
        List<WeekPlanExcelDTO> dataList = EasyExcel.read(file.getInputStream())
                .head(WeekPlanExcelDTO.class)
                .sheet()
                .doReadSync();

        result.setTotal(dataList.size());
        List<WeekPlan> validPlans = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            WeekPlanExcelDTO dto = dataList.get(i);
            Set<ConstraintViolation<WeekPlanExcelDTO>> violations = validator.validate(dto);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<WeekPlanExcelDTO> violation : violations) {
                    result.getErrors().add(new BatchImportResult.ImportError(
                            i + 2, violation.getPropertyPath().toString(), violation.getMessage()));
                }
                result.setFailed(result.getFailed() + 1);
            } else {
                WeekPlan plan = convertToEntity(dto, userId);
                validPlans.add(plan);
            }
        }

        if (!validPlans.isEmpty()) {
            weekPlanRepository.saveAll(validPlans);
            result.setSuccess(validPlans.size());
        }

        return result;
    }

    @Transactional
    public void batchUpdateStatus(List<Long> ids, PlanStatus status, String remark) {
        List<WeekPlan> plans = weekPlanRepository.findAllById(ids);
        for (WeekPlan plan : plans) {
            plan.setStatus(status);
        }
        weekPlanRepository.saveAll(plans);
    }

    @Transactional
    public void batchDelete(List<Long> ids) {
        weekPlanRepository.deleteAllById(ids);
    }

    private WeekPlan convertToEntity(WeekPlanExcelDTO dto, Long userId) {
        WeekPlan plan = new WeekPlan();
        plan.setUserId(userId);
        plan.setTitle(dto.getTitle());
        plan.setContent(dto.getContent());
        plan.setYear(dto.getYear());
        plan.setWeekNumber(dto.getWeekNumber());
        plan.setPriority(dto.getPriority() != null ? dto.getPriority() : "MEDIUM");
        plan.setTags(dto.getTags());
        return plan;
    }
}
