package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ServOrderWorkorderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServOrderWorkorderService servOrderWorkorderService;

    @Test
    void itShouldPrintServiceWorkorderBySeotId() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sowo?sowoid={sowoid}", 1L)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void itShouldUpdateWorkorderById() throws Exception {

        SoWorkorderDto soWorkorderDto = new SoWorkorderDto();
        soWorkorderDto.setSowoId(1L);
        soWorkorderDto.setSowoName("CHECK UMUR");
        soWorkorderDto.setSowoStatus(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/sowo/update/{sowoId}", soWorkorderDto.getSowoId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(soWorkorderDto)))
                .andExpect(status().isOk())
                .andDo(result -> objectMapper.writeValueAsString(soWorkorderDto));

        verify(servOrderWorkorderService, times(1)).updateSowoStatus(true, soWorkorderDto.getSowoId());
    }
}