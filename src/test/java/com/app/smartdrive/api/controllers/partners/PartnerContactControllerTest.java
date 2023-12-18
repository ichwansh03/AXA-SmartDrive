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
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        partnerContactRepository.findByPartner(partner).stream()
                .forEach(partnerContact -> userRepository.delete(partnerContact.getUser()));
        userRepository.deleteById(partner.getPartEntityid());
        partnerRepository.delete(partner);

    }

    Partner createPartner(){
        PartnerRequest request = new PartnerRequest();
        request.setPartName("PARTNER TEST");
        request.setCityId(1L);
        request.setPartNpwp("405");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("123");

        return partnerService.save(request);
    }

    PartnerContactRequest createPartnerContactRequest(boolean grantUser){
        PartnerContactRequest partnerContactRequest = new PartnerContactRequest();
        partnerContactRequest.setPartnerId(partner.getPartEntityid());
        partnerContactRequest.setName("PC TEST");
        partnerContactRequest.setPhone("404");
        partnerContactRequest.setGrantUserAccess(grantUser);

        return partnerContactRequest;
    }

    @BeforeEach
    void setUp() {
        partner = createPartner();
    }

    @Test
    void whenDeletePartnerContact_thenSuccess() throws Exception {
        PartnerContactRequest request = createPartnerContactRequest(true);

        PartnerContact partnerContact = partnerContactService.create(request);

        mockMvc.perform(
                delete("/partner-contacts/"+partnerContact.getId().getUserId())
        ).andExpectAll(
                status().isNoContent()
        );

        assertThrows(EntityNotFoundException.class, ()-> partnerContactService.getById(partnerContact.getId()));
    }
    @Test
    void whenCreatePartnerContactWithGrantUserAccessTrue_thenSuccess() throws Exception {

        PartnerContactRequest partnerContactRequest = createPartnerContactRequest(true);

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

        PartnerContactRequest partnerContactRequest = createPartnerContactRequest(false);

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

        PartnerContactRequest partnerContactRequest = createPartnerContactRequest(false);

        PartnerContact partnerContact = partnerContactService.create(partnerContactRequest);
        log.info("Name contact " + partnerContact.getUser().getUserFullName());
        log.info("Contact " + partnerContact.getUser().getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber());


        PartnerContactRequest partnerContactRequest2 = createPartnerContactRequest(false);
        partnerContactRequest2.setName("UPDATE");
        partnerContactRequest2.setPhone("1111111111");

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