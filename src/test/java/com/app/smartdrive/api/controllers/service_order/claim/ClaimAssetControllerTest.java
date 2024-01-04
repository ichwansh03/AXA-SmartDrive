
package com.app.smartdrive.api.controllers.service_order.claim;

import com.app.smartdrive.api.dto.customer.request.CustomerInscAssetsRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.dto.service_order.response.ServiceDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.CaevRepository;
import com.app.smartdrive.api.repositories.service_orders.CaspRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetServiceImpl;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceFactory;
import com.app.smartdrive.api.services.service_order.servorder.ServiceOrderFactory;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
class ClaimAssetControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    @Autowired
    ClaimAssetServiceImpl claimAssetService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CustomerRequestService customerRequestService;
    @Autowired
    ServiceFactory servService;
    @Autowired
    SoOrderRepository soOrderRepository;
    @Autowired
    ServiceOrderFactory servOrderService;
    @Autowired
    CaspRepository caspRepository;
    @Autowired
    PartnerService partnerService;
    @Autowired
    CaevRepository caevRepository;
    @Autowired
    PartnerRepository partnerRepository;

    User user;
    Partner partner;
    ServiceOrders serviceOrders;
    void deleteUser(User user, Partner partner){
        userRepository.deleteByUserName(user.getUserName());
        userRepository.deleteById(partner.getPartEntityid());
        partnerRepository.delete(partner);
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteUser(user, partner);
        FileUtils.deleteDirectory(new File("src/main/resources/images/caev"));

    }

    @BeforeEach
    void setUp() throws Exception {
        claimAssetService.init();

        partner = create();
        user = userService.save(createUser("TEST405"));
        CustomerRequest customerRequest = createCustomerRequest("CREQTEST123", user);
        ServiceDto services = servService.addService(customerRequest.getCreqEntityId());
        serviceOrders = servOrderService.addServiceOrders(services.getServId());
        serviceOrders.setPartner(partner);
        soOrderRepository.save(serviceOrders);

    }

    User createUser(String test){
        ProfileRequestDto profil = new ProfileRequestDto();
        profil.setUserName(test);
        profil.setUserPassword(test);
        profil.setUserEmail(test);
        profil.setUserNpwp(test);
        profil.setUserBirthDate(LocalDateTime.now().minusYears(30));
        profil.setUserNationalId(test);
        profil.setUserFullName(test);

        return userService.createUser(profil);
    }

    CustomerRequest createCustomerRequest(String test, User user) throws Exception {
        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = new CustomerInscAssetsRequestDTO();
        customerInscAssetsRequestDTO.setCiasPoliceNumber(test);
        customerInscAssetsRequestDTO.setCiasYear("2010");
        customerInscAssetsRequestDTO.setCiasIsNewChar('Y');
        customerInscAssetsRequestDTO.setCiasPaidType("CASH");
        customerInscAssetsRequestDTO.setCiasCarsId(1L);
        customerInscAssetsRequestDTO.setCiasCityId(1L);
        customerInscAssetsRequestDTO.setCiasIntyName("Comprehensive");
        customerInscAssetsRequestDTO.setCiasStartdate("2023-01-01 15:02:00");
        customerInscAssetsRequestDTO.setCurrentPrice(new BigDecimal("120000000"));
        customerInscAssetsRequestDTO.setCuexIds(new Long[]{7L,8L,9L});
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setCustomerId(user.getUserEntityId());
        customerRequestDTO.setAgenId(1L);
        customerRequestDTO.setEmployeeId(1L);
        customerRequestDTO.setCustomerInscAssetsRequestDTO(customerInscAssetsRequestDTO);

        return TransactionMapper.mapDtoToEntity(customerRequestService.create(customerRequestDTO,
                new MultipartFile[]{new MockMultipartFile("TEST", "TEST", MediaType.TEXT_PLAIN_VALUE, "TEST".getBytes())}
        ), new CustomerRequest());
    }
    Partner create(){
        PartnerRequest request = new PartnerRequest();
        request.setPartName("COMPANY 123");
        request.setCityId(1L);
        request.setPartNpwp("111111111");
        request.setPartAddress("JL BENGKEL");
        request.setPartAccountNo("1111");

        Partner partner = partnerService.save(request);
        return partnerService.save(partner);
    }

    @Test
    void whenPostClaimAssetEvidence_thenSuccess() throws Exception {

        InputStream inputStream = Files.newInputStream(Path.of("src/main/resources/images/java.png"));
        MockMultipartFile file1 = new MockMultipartFile("claimAssets[0].file", "java.png", MediaType.IMAGE_PNG_VALUE, inputStream);

        mockMvc.perform(
                multipart("/claim-asset/evidence")
                        .file(file1)
                        .param("claimAssets[0].note", "Note A")
                        .param("partnerId", partner.getPartEntityid().toString())
                        .param("serviceOrderId", serviceOrders.getSeroId())
                        .param("claimAssets[0].serviceFee", "2500000")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            List<ClaimAssetEvidence> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ClaimAssetEvidence>>() {});
            log.info(objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(response));
        });
    }

    @Test
    void whenPostClaimAssetSparePart_thenSuccess() throws Exception {

        mockMvc.perform(
                post("/claim-asset/spare-part")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("partnerId", partner.getPartEntityid().toString())
                        .param("serviceOrderId", serviceOrders.getSeroId())
                        .param("claimAssets[0].item", "KACA DEPAN")
                        .param("claimAssets[0].Qty", "1")
                        .param("claimAssets[0].price", "500000")
                        .param("claimAssets[0].totalPrice", "500000")
                        .param("claimAssets[1].item", "SPION")
                        .param("claimAssets[1].Qty", "2")
                        .param("claimAssets[1].price", "500000")
                        .param("claimAssets[1].totalPrice", "1000000")
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            List<ClaimAssetSparepart> caspList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ClaimAssetSparepart>>() {});
            Assertions.assertEquals(2, caspList.size());
            Assertions.assertEquals(1000000.0, caspList.get(1).getCaspSubtotal());
        });
    }
}

