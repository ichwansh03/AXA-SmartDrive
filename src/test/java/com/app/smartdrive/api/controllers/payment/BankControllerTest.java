// package com.app.smartdrive.api.controllers.payment;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import com.app.smartdrive.api.config.SecurityConfig;

// import lombok.extern.slf4j.Slf4j;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.springframework.test.web.servlet.MockMvcBuilder.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

// @WebMvcTest(BanksController.class )
// @ImportAutoConfiguration(classes = {SecurityConfig.class})
// @Slf4j
// public class BankControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Test
//     void whenGetBankIdThenSuccess() throws Exception {
//         mockMvc.perform(get("/banks/20"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        .andDo(result -> {
//            log.info(result.getResponse().getContentAsString());
//        });

 
//     }


    
    
    
// }
