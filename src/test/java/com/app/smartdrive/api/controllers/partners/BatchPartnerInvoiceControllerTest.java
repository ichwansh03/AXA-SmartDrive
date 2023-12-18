package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.config.JwtAuthenticationFilter;
import com.app.smartdrive.api.config.WebSecurityConfig;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class BatchPartnerInvoiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BatchPartnerInvoiceService batchPartnerInvoiceService;


    @Test
    void whenCreateInvoice_thenSuccess() throws Exception {

        ServiceOrders serviceOrders = new ServiceOrders();
        Partner partner = new Partner();
        partner.setPartEntityid(1L);
        serviceOrders.setPartner(partner);
        serviceOrders.setSeroId("SO TEST");

        BatchPartnerInvoice bpin = BatchPartnerInvoice.builder()
                .no("BPIN TEST")
                .partner(partner)
                .serviceOrders(serviceOrders)
                .subTotal(100D)
                .status(BpinStatus.NOT_PAID)
                .tax(100D*0.1)
                .build();

        when(batchPartnerInvoiceService.createOne(serviceOrders.getSeroId())).thenReturn(bpin);

        mockMvc.perform(
                post("/partner-invoice")
                        .param("seroId", serviceOrders.getSeroId())
        ).andExpectAll(
                status().isOk()
        ).andDo(print());
        verify(batchPartnerInvoiceService, times(1)).createOne(serviceOrders.getSeroId());
    }
    @Test
    void whenGetInvoiceNotPaid_thenSuccess() throws Exception {
        ServiceOrders serviceOrders = new ServiceOrders();
        Partner partner = new Partner();
        partner.setPartEntityid(1L);
        serviceOrders.setPartner(partner);
        serviceOrders.setSeroId("SO TEST");

        BatchPartnerInvoice bpin = BatchPartnerInvoice.builder()
                .no("BPIN TEST")
                .partner(partner)
                .serviceOrders(serviceOrders)
                .subTotal(100D)
                .status(BpinStatus.NOT_PAID)
                .tax(100D*0.1)
                .build();

        List<BatchPartnerInvoice> batchPartnerInvoiceList = new ArrayList<>();
        batchPartnerInvoiceList.add(bpin);

        when(batchPartnerInvoiceService.getAllInvoiceNotPaid()).thenReturn(batchPartnerInvoiceList);
        mockMvc.perform(
                get("/partner-invoice")
        ).andExpectAll(
                status().isOk()
        ).andDo(print());
        verify(batchPartnerInvoiceService, times(1)).getAllInvoiceNotPaid();
    }
}