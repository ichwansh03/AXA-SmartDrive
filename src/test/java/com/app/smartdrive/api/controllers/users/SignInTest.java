//package com.app.smartdrive.api.controllers.users;
//
//import com.app.smartdrive.api.dto.auth.request.SignInRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.Cookie;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
public class SignInTest {
//  @Autowired
//  MockMvc mockMvc;
//  @Autowired
//  private WebApplicationContext context;
//  @Autowired
//  private Filter springSecurityFilterChain;
//  @Autowired
//  ObjectMapper objectMapper;
//
//  Cookie cookie;
//
//  @BeforeEach
//  void setUp() {
//    mockMvc = MockMvcBuilders.webAppContextSetup(context)
//            .addFilters(springSecurityFilterChain).build();
//  }
//
//  @BeforeEach
//  void setUp() throws Exception {
//    login("admin", "admin")
//            .andDo(result -> cookie = result.getResponse().getCookie("cookiejwt"));
//  }
//
//  ResultActions login(String username, String password) throws Exception {
//    SignInRequest loginDto = SignInRequest.builder()
//            .username(username)
//            .password(password)
//            .build();
//    return mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(loginDto)));
//  }
//
//  @Test
//  void shouldSignIn() throws Exception {
//    SignInRequest loginDto = SignInRequest.builder()
//            .username("admin")
//            .password("admin")
//            .build();
//    mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(loginDto)))
//            .andExpect(status().isOk())
//            .andDo(print());
//  }
//
//  @Test
//  void shouldGetUserById() throws Exception {
//    mockMvc.perform(get("/user/58").contentType(MediaType.APPLICATION_JSON)
//                    )
//            .andExpect(status().isOk())
//            .andDo(print());
//  }
}
