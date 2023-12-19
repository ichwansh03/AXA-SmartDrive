package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.Exceptions.Error;
import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestTypeDTO;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
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
    private CustomerRequestService customerRequestService;
    @Autowired
    PartnerService partnerService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    @Autowired
    SoOrderRepository soOrderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServService service;
    @Autowired
    ServOrderService servOrderService;
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    CustomerRequestRepository customerRequestRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        partnerRepository.deleteAll();
        userRepository.deleteByUserName("TEST123");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    User createUser(String test){
        ProfileRequestDto profil = new ProfileRequestDto();
        profil.setUserName(test);
        profil.setUserPassword(test);
        profil.setUserEmail(test);
        profil.setUserNpwp(test);
        profil.setUserBirthDate(LocalDateTime.now().minusYears(36));
        profil.setUserNationalId(test);
        profil.setUserFullName(test);

        return userService.createUser(profil);
    }

    CustomerRequest createCustomerRequest(String test, User user) throws Exception {
        CiasDTO ciasDTO = new CiasDTO();
        ciasDTO.setCiasPoliceNumber(test);
        ciasDTO.setCiasYear("2010");
        ciasDTO.setCiasIsNewChar('Y');
        ciasDTO.setCiasPaidType("CASH");
        ciasDTO.setCiasCarsId(1L);
        ciasDTO.setCiasCityId(1L);
        ciasDTO.setCiasIntyName("Comprehensive");
        ciasDTO.setCiasStartdate("2023-01-01 15:02:00");
        ciasDTO.setCurrentPrice(new BigDecimal(120000000));
        ciasDTO.setCuexIds(new Long[]{7L,8L,9L});
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setCustomerId(user.getUserEntityId());
        customerRequestDTO.setAgenId(1L);
        customerRequestDTO.setEmployeeId(1L);
        customerRequestDTO.setCiasDTO(ciasDTO);

        return TransactionMapper.mapDtoToEntity(customerRequestService.create(customerRequestDTO,
                new MultipartFile[]{new MockMultipartFile("TEST", "TEST", MediaType.TEXT_PLAIN_VALUE, "TEST".getBytes())}
        ), new CustomerRequest());
    }


    @Test
    void whenGetAllServiceByPartner() throws Exception {
        User user = userRepository.save(createUser("TEST123"));
        CustomerRequest customerRequest = createCustomerRequest("CREQ",user);
        //customerRequestService.changeRequestTypeToClaim(new CustomerRequestTypeDTO(customerRequest.getCreqEntityId()));
        Services services = service.addService(customerRequest.getCreqEntityId());
        ServiceOrders serviceOrders = servOrderService.addServiceOrders(services.getServId());
        Partner partner = create();
        serviceOrders.setPartner(partner);
        soOrderRepository.save(serviceOrders);

        mockMvc.perform(
                get("/partners/workorder")
                        .header("PARTNER-ID", partner.getPartEntityid())
        ).andExpectAll(
                status().isOk()
        );
    }
    @Test
    void whenDelete_thenSucces() throws Exception {
        Partner partner = create();

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
        request.setPartName("1234567890");
        request.setCityId(1L);
        request.setPartNpwp("1234567890");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("123");

        Partner partner = partnerService.save(request);
        return partnerService.save(partner);
    }
    @Test
    void whenUpdatePartner_thenSuccess() throws Exception {
        Partner partner = create();
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

        Partner partner = create();
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