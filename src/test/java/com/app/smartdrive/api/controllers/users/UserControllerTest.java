package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.controllers.auth.AuthenticationController;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.refreshToken.RefreshTokenService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest({UserController.class, AuthenticationController.class})
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
  private AuthenticationService authenticationService;
  @MockBean
  private RefreshTokenService refreshTokenService;
  @MockBean
  private JwtService jwtService;
  private User user;
  @MockBean
  private ClaimAssetService claimAssetService;
  private RequestBuilder requestBuilder;

  @BeforeEach
  void configurationSystemUnderTest() throws Exception{
    requestBuilder = new RequestBuilder(mockMvc, objectMapper);
    user = requestBuilder.createUser();
  }

  @Nested
  @DisplayName("Test Get User")
  class GetUser {
      @Test
      @WithMockUser("users")
      @DisplayName("Should return user by id")
      void shouldReturnUser () throws Exception {
      Long id = 2L;
      UserDto userDto = TransactionMapper.mapEntityToDto(user, UserDto.class);
      when(userService.getById(2L)).thenReturn(user);
      requestBuilder.getUserByIdMock(id)
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

    @Test
    public void shouldReturnUnauthorize() throws Exception{
        requestBuilder.getUserByIdMock(1L)
                .andExpect(status().isUnauthorized());
    }
  }

  @Nested
  @DisplayName("Test create user")
  class CreateUser{
    @Test
    @WithMockUser(authorities = "Admin")
    void shouldCreateUserCustomer() throws Exception {
      CreateUserDto createUserDto = objectMapper.readValue(
              new File("src/main/resources/request/CreateUserDto.json"),
              CreateUserDto.class);

      mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
              .with(user("users").
                      authorities(List.of(new SimpleGrantedAuthority("Admin"))))
              .with(csrf())
              .content(objectMapper.writeValueAsString(createUserDto)))
              .andExpect(status().isCreated())
              .andDo(print());
    }
  }

  @Nested
  @DisplayName("Test Update User")
  class UpdateUser{
    @Test
    @WithMockUser(authorities = "Admin")
    void shouldUpdateUser() throws Exception{
      Long id = 1L;
      UpdateUserRequestDto updateRequest = objectMapper.readValue(
              new File("src/main/resources/request/UpdateUserRequestDto.json"),
              UpdateUserRequestDto.class);
      UpdateUserRequestDto updatedUser = TransactionMapper.mapEntityToDto(user, UpdateUserRequestDto.class);
      when(userService.updateUser(updateRequest, 1L))
              .thenReturn(updatedUser);
      mockMvc.perform(patch("/user/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                      .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                      .with(csrf())
                      .content(objectMapper.writeValueAsString(updateRequest)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.userName").value(updatedUser.getUserName()))
              .andDo(print());
    }
  }

  @Nested
  @DisplayName("Test Delete User")
  class DeleteUser{
    void shouldDeleteUser() throws Exception {
      Long id = 1L;
      doNothing().when(userService).deleteById(id);
      mockMvc.perform(delete("/user/{id}", id)
                      .with(user("users")
                              .authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                      .with(csrf()))
              .andExpect(status().isOk())
              .andDo(print());
    }
  }
}