package com.github.linfeng.plan.controller;

import com.github.linfeng.plan.entity.PlanWeeks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class WeekPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetWeekPlans() throws Exception {
        mockMvc.perform(get("/api/week-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreateWeekPlan() throws Exception {
        String requestBody = """
            {
                "title": "测试计划",
                "beginDate": %d,
                "endDate": %d,
                "content": "测试内容",
                "status": 0,
                "year": 2026,
                "week": 20
            }
            """.formatted(System.currentTimeMillis(), System.currentTimeMillis() + 7 * 24 * 3600 * 1000);

        mockMvc.perform(post("/api/week-plans")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWeekPlanById() throws Exception {
        mockMvc.perform(get("/api/week-plans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weekId").value(1));
    }

    @Test
    void testUpdateWeekPlan() throws Exception {
        String requestBody = """
            {
                "title": "更新后的计划",
                "status": 1
            }
            """;

        mockMvc.perform(put("/api/week-plans/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteWeekPlan() throws Exception {
        mockMvc.perform(delete("/api/week-plans/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSummary() throws Exception {
        mockMvc.perform(get("/api/week-plans/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists());
    }
}
