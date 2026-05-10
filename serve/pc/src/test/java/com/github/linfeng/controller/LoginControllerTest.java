package com.github.linfeng.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginSuccess() throws Exception {
        String requestBody = "{" +
                "\"username\": \"testuser\"," +
                "\"password\": \"123456\"" +
            "}";

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testLoginFailure() throws Exception {
        String requestBody = "{" +
                "\"username\": \"testuser\"," +
                "\"password\": \"wrongpassword\"" +
            "}";

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginWithInvalidUser() throws Exception {
        String requestBody = "{" +
                "\"username\": \"nonexistent\"," +
                "\"password\": \"123456\"" +
            "}";

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginWithMissingUsername() throws Exception {
        String requestBody = "{" +
                "\"password\": \"123456\"" +
            "}";

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithMissingPassword() throws Exception {
        String requestBody = "{" +
                "\"username\": \"testuser\"" +
            "}";

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithEmptyBody() throws Exception {
        String requestBody = "{}";

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
