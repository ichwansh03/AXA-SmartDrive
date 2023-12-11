package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class BatchPartnerInvoiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BatchPartnerInvoiceRepository batchPartnerInvoiceRepository;

    @Test
    void whenCreateInvoice_thenSuccess() throws Exception {

        batchPartnerInvoiceRepository.deleteAll();

        mockMvc.perform(
                post("/partner-invoice")
                        .param("seroId", "CL0001-20231010")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            BatchPartnerInvoice bpin = objectMapper.readValue(result.getResponse().getContentAsString(), BatchPartnerInvoice.class);

            assertNotNull(bpin);
            log.info(objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(bpin));

        });
    }
    @Test
    void whenGetInvoiceNotPaid_thenSuccess() throws Exception {


        mockMvc.perform(
                get("/partner-invoice")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            List<BatchPartnerInvoice> bpin = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<BatchPartnerInvoice>>() {});

            assertNotNull(bpin);
            log.info(objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(bpin));

        });
    }
}