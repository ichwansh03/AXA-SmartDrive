package com.app.smartdrive.servicetest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceOrdersTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getServicesByIdTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/service/serv?id=1")
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getSeroByIdTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/service/servorder?seroid=PL0001-20231126")
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }
}
