package com.app.smartdrive.api.controllers.service_order.servorder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ServControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void getServiceByIdTest_Success() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/service?servid={servid}",1L)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.servVehicleNumber").value("BE 2090 OE"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getServiceByIdTest_Notfound() throws Exception {
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/service?servid={servid}",999L)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }

}
