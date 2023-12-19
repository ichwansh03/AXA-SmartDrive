package com.app.smartdrive.api.controllers.service_order.premi;


import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ServicePremiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServPremiCreditService servPremiCreditService;

//    @DisplayName("Update Premi Credit by SecrId")
//    @Test
//    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
//    void itShouldUpdatePremiCreditBySecrId() throws Exception {
//        SecrReqDto premiCredit = new SecrReqDto();
//        premiCredit.setSecrId(1L);
//        premiCredit.setSecrServId(1L);
//        premiCredit.setSecrYear("2023");
//        premiCredit.setSecrPremiDebet(BigDecimal.valueOf(100000));
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/premi/credit/update/{secrServId}/{secrId}", premiCredit.getSecrServId(), premiCredit.getSecrId())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(premiCredit)))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(servPremiCreditService, times(1)).updateSecr(premiCredit, premiCredit.getSecrId(), premiCredit.getSecrServId());
//    }
}