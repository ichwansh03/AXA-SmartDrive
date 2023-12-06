package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.partner.PartnerContactRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.partner.PartnerContactService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.function.Consumer;

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
    PartnerRepository partnerRepository;

    @Autowired
    PartnerContactRepository partnerContactRepository;

    @Autowired
    PartnerContactService partnerContactService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    Partner partner;

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

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        partner = partnerService.save(request);
    }

    @Test
    void whenDeletePartnerContact_thenSuccess() throws Exception {

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("TEST");
        partnerContactRequest.setPhone("089999999998");
        partnerContactRequest.setGrantUserAccess(true);

        PartnerContact partnerContact = partnerContactService.create(partnerContactRequest);

        mockMvc.perform(
                delete("/partner-contacts/"+partnerContact.getId().getUserId())
        ).andExpectAll(
                status().isNoContent()
        );

        assertThrows(EntityNotFoundException.class, ()-> partnerContactService.getById(partnerContact.getId()));
    }
    @Test
    void whenCreatePartnerContactWithGrantUserAccessTrue_thenSuccess() throws Exception {

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("TEST");
        partnerContactRequest.setPhone("089999999998");
        partnerContactRequest.setGrantUserAccess(true);

        mockMvc.perform(
                post("/partner-contacts")
                        .content(objectMapper.writeValueAsString(partnerContactRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            PartnerContactDto partnerContactDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerContactDto.class);
            User user = userRepository.findById(partnerContactDto.getId().getUserId()).get();
            assertEquals(partnerContactRequest.getPhone(), partnerContactDto.getUser().getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber());
            assertEquals(partnerContactRequest.getName(), partnerContactDto.getUser().getUserFullName());
            assertEquals(partnerContactRequest.getPhone(), user.getUserPassword());
            log.info(objectMapper.writeValueAsString(partnerContactDto));
        });
    }

    @Test
    void whenCreatePartnerContactWithGrantUserAccessFalse_thenSuccess() throws Exception {

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("TEST");
        partnerContactRequest.setPhone("089999999998");
        partnerContactRequest.setGrantUserAccess(false);

        mockMvc.perform(
                post("/partner-contacts")
                        .content(objectMapper.writeValueAsString(partnerContactRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            PartnerContactDto partnerContactDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerContactDto.class);
            User user = userRepository.findById(partnerContactDto.getId().getUserId()).get();
            assertEquals(partnerContactRequest.getPhone(), partnerContactDto.getUser().getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber());
            assertEquals(partnerContactRequest.getName(), partnerContactDto.getUser().getUserFullName());
            assertNull(user.getUserPassword());
            log.info(objectMapper.writeValueAsString(partnerContactDto));
        });
    }

    @Test
    void whenEditPartnerContact_thenSuccess() throws Exception {

        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("TEST");
        partnerContactRequest.setPhone("888888888888");
        partnerContactRequest.setGrantUserAccess(false);

        PartnerContact partnerContact = partnerContactService.create(partnerContactRequest);
        log.info("Name contact " + partnerContact.getUser().getUserFullName());
        log.info("Contact " + partnerContact.getUser().getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber());


        PartnerContactRequest partnerContactRequest2 = new PartnerContactRequest();
        partnerContactRequest2.setPartnerId(partner.getPartEntityid());
        partnerContactRequest2.setName("TEST UPDATE");
        partnerContactRequest2.setPhone("999999999999");
        partnerContactRequest2.setGrantUserAccess(true);

        mockMvc.perform(
                put("/partner-contacts/"+partnerContact.getId().getUserId())
                        .content(objectMapper.writeValueAsString(partnerContactRequest2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            PartnerContactDto partnerContactDto = objectMapper.readValue(result.getResponse().getContentAsString(), PartnerContactDto.class);
            assertEquals(partnerContactRequest2.getName(), partnerContactDto.getUser().getUserFullName());
            log.info(objectMapper.writeValueAsString(partnerContactDto));
        });
    }
}