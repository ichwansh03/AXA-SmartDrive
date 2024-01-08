package com.app.smartdrive.api.controllers.payment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

//import com.app.smartdrive.api.services.payment.BankService;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class BankControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    BankService bankService;
//
//    @Autowired
//    private ObjectMapper objectmapper;
//
//
//
//    BanksDtoRequests createDtoBanks(){
//        BanksDtoRequests requests = new BanksDtoRequests();
//        requests.setBank_name("BCA");
//        requests.setBank_desc("BANK SWASTA");
//
//        return requests;
//    }
//
//    @Test
//    @WithMockUser(authorities = {"Admin"})
//    void whenGetBankIdThenSuccess() throws Exception {
//        BanksDtoRequests requests = createDtoBanks();
//
////        Banks banks = new Banks();
////        banks.setBank_entityid(1L);
////        Long bank_entityid = banks.getBank_entityid();
////         BanksDtoResponse response = new BanksDtoResponse();
////         response.setBank_entityid(bank_entityid);
//
//        BanksDtoResponse banksDtoResponse = new BanksDtoResponse();
//
//        when(bankService.getById(1L)).thenReturn(banksDtoResponse);
//        mockMvc.perform(get("/payment/banks/{bank_entityid}", 1))
//       .andExpect(status().isOk())
//       .andDo(print());
//    }
//
//    @Test
//    @WithMockUser(authorities = {"Admin"})
//    void updateBanks() throws Exception{
//        BanksDtoResponse dto = new BanksDtoResponse();
//        BanksDtoRequests requests = createDtoBanks();
//        dto.setBank_name("BDA");
//        dto.setBank_desc("SWASTA");
//
//        TransactionMapper.mapDtoToEntity(requests, dto);
//        when(bankService.updateBanks(1L, dto)).thenReturn(true);
//
//        mockMvc.perform(put("/payment/banks/update/{bank_entityid}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
//                .with(csrf())
//                .content(objectmapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(result -> jsonPath("$.bank_name").value(dto.getBank_name()))
//                .andDo(print());
//    }
//
//
//    @Test
//    @WithMockUser(authorities = {"Admin"})
//    void addBanks() throws Exception{
//        BanksDtoResponse dto = new BanksDtoResponse();
//        BanksDtoRequests requests = createDtoBanks();
//        requests.setBank_name("BTN");
//        requests.setBank_desc("SWASTA");
//
//        TransactionMapper.mapDtoToEntity(requests, dto);
//        when(bankService.addBankss(requests)).thenReturn(dto);
//
//        mockMvc.perform(post("/payment/banks/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
//                .with(csrf())
//                .content(objectmapper.writeValueAsString(requests)))
//                .andExpect(status().isCreated())
//                .andExpect(result -> jsonPath("$.bank_name").value(requests.getBank_name()))
//                .andDo(print());
//    }
//
}
