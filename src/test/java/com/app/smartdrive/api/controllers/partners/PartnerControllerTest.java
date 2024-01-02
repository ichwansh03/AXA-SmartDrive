package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.Exceptions.Error;
import com.app.smartdrive.api.dto.customer.request.CustomerInscAssetsRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PartnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PartnerService partnerService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PartnerRepository partnerRepository;

    Partner partner;

    @BeforeEach
    @Transactional
    public void setUp() {
        userRepository.deleteByUserName("TEST123");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @AfterEach
    void tearDown() {
        partnerRepository.delete(partner);
    }

    @Test
    void whenGetAllServiceByPartner() throws Exception {
        partner = create();

        mockMvc.perform(
                get("/partners/workorder")
                        .param("entityid", "1060")
                        .param("arwgcode", "BCY-0001")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(print());
    }
    @Test
    void whenDelete_thenSucces() throws Exception {
        partner = create();

        mockMvc.perform(
                delete("/partners/"+partner.getPartEntityid())
        ).andExpectAll(
                status().is(204)
        );

        mockMvc.perform(
                get("/partners/"+partner.getPartEntityid())
        ).andExpectAll(
                status().isNotFound()
        );

    }

    Partner create(){
        PartnerRequest request = new PartnerRequest();
        request.setPartName("PARTNER TEST");
        request.setCityId(1L);
        request.setPartNpwp("123123123");
        request.setPartAddress("SENTUL");
        request.setPartAccountNo("123123123");

        Partner partner = partnerService.save(request);
        return partnerService.save(partner);
    }
    @Test
    void whenUpdatePartner_thenSuccess() throws Exception {
        partner = create();
        PartnerRequest request = new PartnerRequest();
        request.setPartName("UPDATE");
        request.setCityId(1L);
        request.setPartNpwp("1234567890");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("123");

        mockMvc.perform(
                put("/partners/"+partner.getPartEntityid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            PartnerDto partnerDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerDto.class);
            assertEquals(partnerDto.getPartName(), request.getPartName());


        });
    }

    @Test
    void whenGetPartnerById_thenNotFound() throws Exception {
        mockMvc.perform(
                get("/partners/999")
        ).andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON)
        ).andDo(result -> {
            Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
            assertEquals("Partner not found by id 999", error.getMessage());


            log.info(error.toString());


        });

    }

    @Test
    void whenGetPartnerById_thenSuccess() throws Exception {

        partner = create();
        mockMvc.perform(
                get("/partners/"+partner.getPartEntityid())
        ).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        ).andDo(result -> {
            PartnerDto partnerDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerDto.class);
            assertEquals(partner.getPartNpwp(), partnerDto.getPartNpwp());
            log.info("resonse {}", objectMapper.writeValueAsString(partnerDto));
        });
    }

    @Test
    void whenCreatePartner_thenBadRequest() throws Exception {


        PartnerRequest request = new PartnerRequest();
        request.setPartName("12345678901234567890123456");
        request.setCityId(null);
        request.setPartNpwp("12345678901234567890123456");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("123");

        mockMvc.perform(
                post("/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {

            log.info("errors {}", result.getResponse().getContentAsString());
        });
    }

    @Test
    void whenCreatePartner_thenSuccess() throws Exception {


        PartnerRequest request = new PartnerRequest();
        request.setPartName("BENGKEL");
        request.setCityId(1L);
        request.setPartNpwp("1");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("1");

        mockMvc.perform(
                post("/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            String responseJson = result.getResponse().getContentAsString();
            PartnerDto partnerDto = objectMapper.readValue(responseJson, PartnerDto.class);
            assertNotNull(partnerDto.getBusinessEntity());
            assertNotNull(partnerDto.getPartEntityid());
            assertEquals("BENGKEL", partnerDto.getPartName());
            String prettyJson = objectMapper.writeValueAsString(partnerDto);
            log.info("Response {}", prettyJson);
        });
     }
}