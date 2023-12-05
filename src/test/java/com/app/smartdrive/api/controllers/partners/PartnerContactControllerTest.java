package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PartnerContactControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PartnerService partnerService;

    @Autowired
    EntityManager em;

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    ObjectMapper objectMapper;

    Partner partner;

    @BeforeEach
    void setUp() {
        partnerRepository.deleteAll();
        PartnerRequest request = new PartnerRequest();
        request.setPartName("1234567890");
        request.setCityId(1L);
        request.setPartNpwp("1234567890");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("123");

        partner = partnerService.create(request);
        partnerService.save(partner);
    }

    @Test
    void whenCreatePartnerContact_thenSuccess() throws Exception {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("TEST");
        partnerContactRequest.setPhone("089999999999");
        partnerContactRequest.setGrantUserAccess(true);

        mockMvc.perform(
                post("/partner-contacts")
                        .content(objectMapper.writeValueAsString(partnerContactRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            em.clear();
            PartnerContactDto partnerContactDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerContactDto.class);
            assertEquals(partnerContactRequest.getPartnerId(), partnerContactDto.getId().getUserId());
            log.info(objectMapper.writeValueAsString(partnerContactDto));
        });

    }
}