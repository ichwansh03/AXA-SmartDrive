package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
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
import com.app.smartdrive.api.services.partner.PartnerAreaWorkgroupService;
import com.app.smartdrive.api.services.partner.PartnerContactService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class PartnerAreaWorkgroupControllerTest {

    Partner partner;

    PartnerContact partnerContact;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PartnerAreaWorkgroupService partnerAreaWorkgroupService;

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

    @Autowired
    PartnerAreaWorkGroupRepository partnerAreaWorkGroupRepository;

    @AfterEach
    void tearDown() {

        partnerContactRepository.findByPartner(partner).stream().forEach(partnerContact1 -> {
            userRepository.delete(partnerContact1.getUser());
        });
        userRepository.deleteById(partner.getPartEntityid());

        partnerRepository.delete(partner);

    }

    @BeforeEach
    void setUp() {

        PartnerRequest request = new PartnerRequest();
        request.setPartName("GALLEY LA COMPANY");
        request.setCityId(1L);
        request.setPartNpwp("5555555555");
        request.setPartAddress("WATER SEVEN");
        request.setPartAccountNo("5555555");

        partner = partnerService.save(request);

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());

        partnerContactRequest.setName("ICE BERG");
        partnerContactRequest.setPhone("089999999999");

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

    @Test
    void whenGetAllPartnerAreaWorkGroup_thenSuccess() throws Exception {
        PartnerAreaWorkgroupRequest request = new PartnerAreaWorkgroupRequest();
        request.setCityId(1L);
        request.setPartnerContactId(partnerContact.getId());

        arwgRepository.findAll().stream().forEach(areaWorkGroup -> {
            request.setAreaWorkgroupId(areaWorkGroup.getArwgCode());
            partnerAreaWorkgroupService.create(request);
        });

        mockMvc.perform(
                get("/partner-area-workgroups")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            List<PartnerAreaWorkgroupDto> partnerAreaWorkgroupDto = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PartnerAreaWorkgroupDto>>() {});
            assertEquals(partnerAreaWorkgroupDto.size(), arwgRepository.findAll().size());
            log.info("result "+objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(partnerAreaWorkgroupDto));
        });
    }

    @Test
    void whenDeleteById_thenSuccess() throws Exception {

        PartnerAreaWorkgroupRequest request = new PartnerAreaWorkgroupRequest();
        request.setCityId(1L);
        request.setPartnerContactId(partnerContact.getId());
        request.setAreaWorkgroupId(arwgRepository.findAll().get(0).getArwgCode());
        PartnerAreaWorkgroup partnerAreaWorkgroup = partnerAreaWorkgroupService.create(request);

        assertNotNull(partnerAreaWorkgroupService.getById(partnerAreaWorkgroup.getId()));
        mockMvc.perform(
                delete("/partner-area-workgroups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partnerAreaWorkgroup.getId()))
        ).andExpectAll(
                status().isNoContent()
        ).andDo(print());

        assertThrows(EntityNotFoundException.class, ()-> partnerAreaWorkgroupService.getById(partnerAreaWorkgroup.getId()));
    }
}