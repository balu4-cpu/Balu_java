package com.examseating;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicPages() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
        mockMvc.perform(get("/search")).andExpect(status().isOk());
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAllAdminPages() throws Exception {
        mockMvc.perform(get("/admin/dashboard")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/students")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/students/new")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/exams")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/exams/new")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/rooms")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/rooms/new")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/seating")).andExpect(status().isOk());
        mockMvc.perform(get("/admin/seating/generate")).andExpect(status().isOk());
    }
}
