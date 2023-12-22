package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.services.master.CarbService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ImportAutoConfiguration(classes = {SecurityConfig.class})
class CarbControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarbService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void findAllData() throws Exception {
        mockMvc.perform(get("/master/carb"))
                .andExpect(status().isOk());
    }

    @Test
    void findDataById() throws Exception {
    }

    @Test
    void saveData() throws Exception {
    }

    @Test
    void updateData() throws Exception {
    }
}