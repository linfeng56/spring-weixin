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
class ExportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testExportWeekPlans() throws Exception {
        mockMvc.perform(get("/api/week-plans/export")
                        .param("format", "word"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/octet-stream"));
    }

    @Test
    void testExportWithYearMonthFilter() throws Exception {
        mockMvc.perform(get("/api/week-plans/export")
                        .param("year", "2026")
                        .param("month", "4")
                        .param("format", "word"))
                .andExpect(status().isOk());
    }

    @Test
    void testExportPdfFormat() throws Exception {
        mockMvc.perform(get("/api/week-plans/export")
                        .param("format", "pdf"))
                .andExpect(status().isOk());
    }
}
