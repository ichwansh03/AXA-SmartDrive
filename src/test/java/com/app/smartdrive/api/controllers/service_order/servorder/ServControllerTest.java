package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.ServiceReqDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ServControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServService servService;

    @ParameterizedTest
    @DisplayName("get service by id")
    @CsvSource({
            "1, 200",
            "999, 200"
    })
    void itShouldPrintServiceById(Long servId, Integer statusCode) throws Exception {

        ServiceRespDto services = ServiceRespDto.builder()
                .servId(servId)
                .servType(EnumCustomer.CreqType.FEASIBLITY)
                .servCreatedOn(LocalDateTime.now())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(7))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE).build();

        when(servService.findServicesById(servId)).thenReturn(services);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/service?servid={servid}",servId))
                .andExpect(status().is(statusCode))
                .andExpect(jsonPath("$.servType").value(services.getServType().toString()))
                .andExpect(jsonPath("$.servStatus").value(services.getServStatus().toString()))
                .andDo(print());

        //make sure the findServicesById() method is only called once in the servService class
        verify(servService, times(1)).findServicesById(servId);
    }

    @Test
    @DisplayName("generate service from customer")
    void itShouldAddServiceFromCustomer() throws Exception {

        Long creqId = 2L;
        Services services = new Services();
        services.setServStatus(EnumModuleServiceOrders.ServStatus.ACTIVE);
        services.setServType(EnumCustomer.CreqType.POLIS);

        when(servService.addService(creqId)).thenReturn(services);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/service/addserv?creqId={creqId}", creqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    ServiceReqDto serviceReqDto = objectMapper.readValue(result.getResponse().getContentAsString(), ServiceReqDto.class);
                    Assertions.assertEquals(serviceReqDto.getServStatus(), EnumModuleServiceOrders.ServStatus.ACTIVE);
                    Assertions.assertEquals(serviceReqDto.getServType(), EnumCustomer.CreqType.POLIS);
                });

        verify(servService, times(1)).addService(creqId);
    }
}
