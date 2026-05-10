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
class SubtaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetSubtasks() throws Exception {
        mockMvc.perform(get("/api/week-plans/1/subtasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetSubtasksForNonExistentPlan() throws Exception {
        mockMvc.perform(get("/api/week-plans/99999/subtasks"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateSubtask() throws Exception {
        String requestBody = "{" +
                "\"title\": \"子任务1\"," +
                "\"description\": \"子任务描述\"" +
            "}";

        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subtaskId").exists());
    }

    @Test
    void testCreateSubtaskForNonExistentPlan() throws Exception {
        String requestBody = "{" +
                "\"title\": \"子任务1\"," +
                "\"description\": \"子任务描述\"" +
            "}";

        mockMvc.perform(post("/api/week-plans/99999/subtasks")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateSubtaskWithMissingTitle() throws Exception {
        String requestBody = "{" +
                "\"description\": \"子任务描述\"" +
            "}";

        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateSubtaskWithEmptyTitle() throws Exception {
        String requestBody = "{" +
                "\"title\": \"\"," +
                "\"description\": \"子任务描述\"" +
            "}";

        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSubtask() throws Exception {
        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content("{\"title\":\"子任务1\",\"description\":\"描述\"}"))
                .andExpect(status().isOk());

        String updateRequestBody = "{" +
                "\"title\": \"更新后的子任务\"," +
                "\"status\": 2," +
                "\"progress\": 100" +
            "}";

        mockMvc.perform(put("/api/week-plans/1/subtasks/1")
                        .contentType("application/json")
                        .content(updateRequestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSubtaskForNonExistentPlan() throws Exception {
        String updateRequestBody = "{" +
                "\"title\": \"更新后的子任务\"," +
                "\"status\": 2," +
                "\"progress\": 100" +
            "}";

        mockMvc.perform(put("/api/week-plans/99999/subtasks/1")
                        .contentType("application/json")
                        .content(updateRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSubtaskWithPartialData() throws Exception {
        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content("{\"title\":\"子任务1\",\"description\":\"描述\"}"))
                .andExpect(status().isOk());

        String updateRequestBody = "{" +
                "\"progress\": 50" +
            "}";

        mockMvc.perform(put("/api/week-plans/1/subtasks/1")
                        .contentType("application/json")
                        .content(updateRequestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSubtask() throws Exception {
        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content("{\"title\":\"待删除子任务\",\"description\":\"描述\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/week-plans/1/subtasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSubtaskForNonExistentPlan() throws Exception {
        mockMvc.perform(delete("/api/week-plans/99999/subtasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSubtaskAssociationWithWeekPlan() throws Exception {
        mockMvc.perform(post("/api/week-plans/1/subtasks")
                        .contentType("application/json")
                        .content("{\"title\":\"关联测试子任务\",\"description\":\"测试子任务与周计划的关联\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subtaskId").exists());

        mockMvc.perform(get("/api/week-plans/1/subtasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}