package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.partner.PartnerAreaWorkGroupRepository;
import com.app.smartdrive.api.repositories.partner.PartnerContactRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.partner.PartnerContactService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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
class PartnerAreaWorkgroupControllerTest {

    Partner partner;

    PartnerContact partnerContact;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArwgRepository areaWorkGroupRepository;

    @Autowired
    PartnerAreaWorkGroupRepository partnerAreaWorkGroupRepository;

    @Autowired
    PartnerService partnerService;

    @Autowired
    PartnerContactService partnerContactService;

    @Autowired
    ArwgRepository arwgRepository;

    @Autowired
    PartnerContactRepository partnerContactRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @BeforeEach
    void setUp() {
        partnerContactRepository.findAll().stream().forEach(partnerContact -> {
            userRepository.deleteById(partnerContact.getId().getUserId());
        });
        partnerRepository.findAll().stream().forEach(partner1 -> {
            userRepository.deleteById(partner1.getPartEntityid());
        });
        partnerRepository.deleteAll();


        PartnerRequest request = new PartnerRequest();
        request.setPartName("1234567890");
        request.setCityId(1L);
        request.setPartNpwp("1234567890");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("123");

        partner = partnerService.save(request);

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("TEST2");
        partnerContactRequest.setPhone("089999999908");
        partnerContactRequest.setGrantUserAccess(true);

        partnerContact = partnerContactService.create(partnerContactRequest);

    }

    @Test
    void whenCreatePartnerAreWorkGroup_thenSuccess() throws Exception {
        PartnerAreaWorkgroupRequest request = new PartnerAreaWorkgroupRequest();
        request.setAreaWorkgroupId(arwgRepository.findAll().get(0).getArwgCode());
        request.setCityId(1L);
        request.setPartnerContactId(partnerContact.getId());

        mockMvc.perform(
                post("/partner-area-workgroups").content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            PartnerAreaWorkgroupDto partnerAreaWorkgroupDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerAreaWorkgroupDto.class);
            log.info("result "+objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(partnerAreaWorkgroupDto));
        });

    }
}