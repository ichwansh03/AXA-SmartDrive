package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.CarbReq;
import com.app.smartdrive.api.dto.master.response.CarbRes;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.master.implementation.CarbServiceImpl;
import com.app.smartdrive.api.services.refreshToken.RefreshTokenService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ImportAutoConfiguration(classes = {SecurityConfig.class})
class CarbControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private CarbServiceImpl carbService;

    @MockBean
    JwtService jwtService;

    CarbReq createCarBrand() {
        CarbReq carbReq = new CarbReq();
        carbReq.setCabrName("Suzuki");
        return carbReq;
    }

    CarbReq updateCarBrand() {
        CarbReq carbReq = new CarbReq();
        carbReq.setCabrName("Suzuki");
        return carbReq;
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void getAllSuccess() throws Exception {
        List<CarBrand> carBrands = List.of(new CarBrand(), new CarBrand());

        List<CarbRes> carBrandResponse = TransactionMapper.mapEntityListToDtoList(carBrands, CarbRes.class);

        Page<CarbRes> mockCarBrandsPage = new PageImpl<>(carBrandResponse);

        when(carbService.getAll(any(Pageable.class))).thenReturn(mockCarBrandsPage);

        mockMvc.perform(get("/master/carb"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findDataById() throws Exception {
    }

    @Test
    void saveData() throws Exception {
    }

    @Test
    void updateData() throws Exception {
    }
}