package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.services.service_order.servorder.orders.ServOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ServOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServOrderService servOrderService;

    @ParameterizedTest
    @ValueSource(strings = {"CL0001-20231010", "FS0001-20231010", "PL0001-20231010"})
    void itShouldPrintServiceOrderById_Success(String seroId) throws Exception {

//        ServiceOrders serviceOrders = ServiceOrders.builder()
//                .seroId(seroId)
//                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
//                .seroStatus(EnumModuleServiceOrders.SeroStatus.OPEN).build();
//
//        when(servOrderService.findServiceOrdersById(seroId)).thenReturn(serviceOrders);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/sero/search?seroId={seroId}", seroId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.seroOrdtType").value(serviceOrders.getSeroOrdtType().toString()))
//                .andExpect(jsonPath("$.seroStatus").value(serviceOrders.getSeroStatus().toString()))
//                .andDo(print());
//
//        verify(servOrderService, times(1)).findServiceOrdersById(seroId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CL0001-20231011", "FS0001-20231011", "PL0001-20231011"})
    void itShouldPrintServiceOrderById_NotFound(String seroId) throws Exception {

//        when(servOrderService.findServiceOrdersById(seroId)).thenThrow(new EntityNotFoundException("ID not found"));
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/sero/search?seroId={seroId}", seroId)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//
//        verify(servOrderService, times(1)).findServiceOrdersById(seroId);
    }
}