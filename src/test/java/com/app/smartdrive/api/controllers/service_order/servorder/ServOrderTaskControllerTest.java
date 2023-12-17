package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ServOrderTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServOrderTaskService servOrderTaskService;

    @DisplayName("Get Tasks by Sero Id")
    @ParameterizedTest
    @ValueSource(strings = {"CL0001-20231010", "FS0001-20231010", "PL0001-20231010"})
    void itShouldPrintServiceTaskBySeroId(String seroId) throws Exception {

//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/seot?seroId={seroId}", seroId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        verify(servOrderTaskService, times(1)).findSeotBySeroId(seroId);
    }

    @Test
    void itShouldUpdateTaskById() throws Exception {

        SoTasksDto soTasksDto = new SoTasksDto();
        soTasksDto.setSeotId(1L);
        soTasksDto.setSeotName("REVIEW & CHECK CUSTOMER REQUEST");
        soTasksDto.setSeotStatus(EnumModuleServiceOrders.SeotStatus.COMPLETED);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/seot/update/{seotId}", soTasksDto.getSeotId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(soTasksDto)))
                .andExpect(status().isOk())
                .andDo(result -> objectMapper.writeValueAsString(soTasksDto));

        verify(servOrderTaskService, times(1)).updateTasksStatus(soTasksDto.getSeotStatus(), soTasksDto.getSeotId());
    }
}