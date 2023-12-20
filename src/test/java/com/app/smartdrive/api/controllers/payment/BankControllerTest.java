package com.app.smartdrive.api.controllers.payment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.payment.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@ImportAutoConfiguration(classes = {SecurityConfig.class})
@Slf4j
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BankService bankService;

    @Autowired
    private ObjectMapper objectmapper;

    BanksDtoRequests createDtoBanks(){
        BanksDtoRequests requests = new BanksDtoRequests();
        requests.setBank_name("BCA");
        requests.setBank_desc("BANK SWASTA");
            
        return requests;
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void whenGetBankIdThenSuccess() throws Exception {
        BanksDtoRequests requests = createDtoBanks();
       
        Banks banks = new Banks();
        banks.setBank_entityid(1L);
        Long bank_entityid = banks.getBank_entityid();
         BanksDtoResponse response = new BanksDtoResponse();
         response.setBank_entityid(bank_entityid);
        when(bankService.getById(bank_entityid)).thenReturn(response);
        mockMvc.perform(get("/banks/{bank_entityid}", bank_entityid))
       .andExpect(status().isOk())
       .andDo(print());

 
    }


    
    
    
}
