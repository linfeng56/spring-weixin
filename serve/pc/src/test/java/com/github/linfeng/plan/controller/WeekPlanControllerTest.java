package com.github.linfeng.plan.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
    void testGetWeekPlansWithStatusFilter() throws Exception {
        mockMvc.perform(get("/api/week-plans")
                        .param("status", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreateWeekPlan() throws Exception {
        long beginDate = System.currentTimeMillis();
        long endDate = System.currentTimeMillis() + 7 * 24 * 3600 * 1000;
        String requestBody = "{" +
                "\"title\": \"测试计划\"," +
                "\"beginDate\": " + beginDate + "," +
                "\"endDate\": " + endDate + "," +
                "\"content\": \"测试内容\"," +
                "\"status\": 0," +
                "\"year\": 2026," +
                "\"week\": 20" +
            "}";

        mockMvc.perform(post("/api/week-plans")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWeekPlanWithMissingTitle() throws Exception {
        long beginDate = System.currentTimeMillis();
        long endDate = System.currentTimeMillis() + 7 * 24 * 3600 * 1000;
        String requestBody = "{" +
                "\"beginDate\": " + beginDate + "," +
                "\"endDate\": " + endDate + "," +
                "\"status\": 0" +
            "}";

        mockMvc.perform(post("/api/week-plans")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWeekPlanWithInvalidDateRange() throws Exception {
        long beginDate = System.currentTimeMillis() + 7 * 24 * 3600 * 1000;
        long endDate = System.currentTimeMillis();
        String requestBody = "{" +
                "\"title\": \"测试计划\"," +
                "\"beginDate\": " + beginDate + "," +
                "\"endDate\": " + endDate + "," +
                "\"status\": 0" +
            "}";

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
    void testGetWeekPlanByNonExistentId() throws Exception {
        mockMvc.perform(get("/api/week-plans/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateWeekPlan() throws Exception {
        String requestBody = "{" +
                "\"title\": \"更新后的计划\"," +
                "\"status\": 1" +
            "}";

        mockMvc.perform(put("/api/week-plans/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateNonExistentWeekPlan() throws Exception {
        String requestBody = "{" +
                "\"title\": \"更新后的计划\"," +
                "\"status\": 1" +
            "}";

        mockMvc.perform(put("/api/week-plans/99999")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteWeekPlan() throws Exception {
        mockMvc.perform(delete("/api/week-plans/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteNonExistentWeekPlan() throws Exception {
        mockMvc.perform(delete("/api/week-plans/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetSummary() throws Exception {
        mockMvc.perform(get("/api/week-plans/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    void testGetSummaryWithType() throws Exception {
        mockMvc.perform(get("/api/week-plans/summary")
                        .param("type", "week"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    void testGetByWeek() throws Exception {
        mockMvc.perform(get("/api/week-plans/week")
                        .param("year", "2026")
                        .param("week", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetByWeekWithNoResults() throws Exception {
        mockMvc.perform(get("/api/week-plans/week")
                        .param("year", "2020")
                        .param("week", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSummary() throws Exception {
        String requestBody = "{" +
                "\"summary\": \"这是更新的总结内容\"" +
            "}";

        mockMvc.perform(put("/api/week-plans/1/summary")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSummaryForNonExistentPlan() throws Exception {
        String requestBody = "{" +
                "\"summary\": \"这是更新的总结内容\"" +
            "}";

        mockMvc.perform(put("/api/week-plans/99999/summary")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetChangeHistory() throws Exception {
        mockMvc.perform(get("/api/week-plans/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetChangeHistoryForNonExistentPlan() throws Exception {
        mockMvc.perform(get("/api/week-plans/99999/history"))
                .andExpect(status().isNotFound());
    }
}