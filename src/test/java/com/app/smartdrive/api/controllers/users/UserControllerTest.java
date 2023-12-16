package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@WebMvcTest(UserController.class)
@ImportAutoConfiguration({SecurityConfig.class})
@DisplayName("User Controller Test")
class UserControllerTest {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService userService;
  @MockBean
  private JwtService jwtService;
  @MockBean
  private ClaimAssetService claimAssetService;

  private RequestBuilder requestBuilder;

  @BeforeEach
  void configurationSystemUnderTest(){
    requestBuilder = new RequestBuilder(mockMvc, objectMapper);
  }

  @Nested
  @DisplayName("Test Get User")
  class TestGetUser {
      @Test
      @WithMockUser(authorities = {"Admin"})
      @DisplayName("Should return user by id")
      void shouldReturnUser () throws Exception {
      Long id = 1L;
      User user = requestBuilder.createUser();
      UserDto userDto = TransactionMapper.mapEntityToDto(user, UserDto.class);
      when(userService.getById(1L)).thenReturn(user);
      requestBuilder.getUserById(id)
              .andExpect(jsonPath("$.userEntityId").value(id))
              .andExpect(jsonPath("$.userPhoto").value(userDto.getUserPhoto()))
              .andExpect(jsonPath("$.userFullName").value(userDto.getUserFullName()))
              .andExpect(jsonPath("$.userEmail").value(userDto.getUserEmail()))
              .andExpect(jsonPath("$.userAddress.size()").value(userDto.getUserAddress().size()))
              .andExpect((jsonPath("$.userAddress[0]").value(userDto.getUserAddress().get(0))))
              .andExpect(jsonPath("$.userAddress[1]").value(userDto.getUserAddress().get(1)))
              .andExpect((jsonPath("$.userPhone.size()").value(userDto.getUserPhone().size())))
              .andExpect(jsonPath("$.userPhone[0]").value(userDto.getUserPhone().get(0)))
              .andExpect(jsonPath("$.userAccounts.size()").value(userDto.getUserAccounts().size()))
              .andExpect(jsonPath("$.userAccounts[0]").value(userDto.getUserAccounts().get(0)))
              .andExpect(jsonPath("$.userRoles.size()").value(userDto.getUserRoles().size()))
              .andExpect(jsonPath("$.userRoles[0]").value(userDto.getUserRoles().get(0)))
              .andDo(print());
    }
  }
}