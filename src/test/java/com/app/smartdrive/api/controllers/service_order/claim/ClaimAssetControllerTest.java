package com.app.smartdrive.api.controllers.service_order.claim;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.repositories.service_orders.CaevRepository;
import com.app.smartdrive.api.repositories.service_orders.CaspRepository;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    ClaimAssetServiceImpl claimAssetService;

    @Autowired
    CaspRepository caspRepository;

    @Autowired
    CaevRepository caevRepository;

    @BeforeEach
    void setUp() throws IOException {
        FileUtils.deleteDirectory(new File("src/main/resources/images/caev"));
        //caevRepository.deleteAll();
        caspRepository.deleteAll();
        claimAssetService.init();


    }

    @Test
    void whenPostClaimAssetEvidence_thenSuccess() throws Exception {

        InputStream inputStream = Files.newInputStream(Path.of("src/main/resources/images/java.png"));
        MockMultipartFile file1 = new MockMultipartFile("claimAssets[0].file", "java.png", MediaType.IMAGE_PNG_VALUE, inputStream);

        mockMvc.perform(
                multipart("/claim-asset/evidence")
                        .file(file1)
                        .param("claimAssets[0].note", "Note A")
                        .param("partnerId", "10")
                        .param("serviceOrderId", "CL0001-20231010")
                        .param("claimAssets[0].serviceFee", "2500000")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            // List<ClaimAssetEvidence> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ClaimAssetEvidence>>() {});
            // log.info(objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(response));
        });
    }

    @Test
    void whenPostClaimAssetSparePart_thenSuccess() throws Exception {

        mockMvc.perform(
                post("/claim-asset/spare-part")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("partnerId", "10")
                        .param("serviceOrderId", "CL0001-20231010")
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
            // List<ClaimAssetSparepart> caspList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ClaimAssetSparepart>>() {});
            // Assertions.assertEquals(2, caspList.size());
            // Assertions.assertEquals(1000000.0, caspList.get(1).getCaspSubtotal());
        });
    }
}