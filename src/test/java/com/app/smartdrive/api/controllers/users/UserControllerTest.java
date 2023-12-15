package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.config.WebSecurityConfig;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Gson gson;

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService userService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private ClaimAssetService claimAssetService;

  List<UserRoles> addRole(EnumUsers.RoleName roleName, User user) {
    UserRolesId userRolesId = new UserRolesId(user.getUserEntityId(), roleName);

    Roles roles = new Roles();
    roles.setRoleDescription("customer");
    roles.setRoleName(roleName);

    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus("ACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());

    List<UserRoles> listRole = new ArrayList<>();
    listRole.add(userRoles);
    user.setUserRoles(listRole);
    return listRole;
  }

  User createUser() throws Exception{
    CreateUserDto createUserDto = objectMapper.readValue(
            new File("src/main/resources/request/CreateUserDto.json"),
            CreateUserDto.class);
    User user = new User();
    user.setUserEntityId(1L);
    user.setUserPhoto("test.png");
    TransactionMapper.mapDtoToEntity(createUserDto.getProfile(), user);
    TransactionMapper.mapDtoToEntity(createUserDto, user);

    addRole(EnumUsers.RoleName.CU, user);

    return user;
  }

  @Test
  @WithMockUser(authorities = {"Admin"})
  void shouldReturnUser() throws Exception{
    Long id = 1L;
    User user = createUser();
    UserDto userDto = TransactionMapper.mapEntityToDto(user, UserDto.class);
    when(userService.getById(1L)).thenReturn(user);
    mockMvc.perform(get("/user/{id}", id))
            .andExpect(jsonPath("$.userEntityId").value(id))
            .andExpect(jsonPath("$.userPhoto").value(userDto.getUserPhoto()))
            .andExpect(jsonPath("$.userFullName").value(userDto.getUserFullName()))
            .andExpect(jsonPath("$.userEmail").value(userDto.getUserEmail()))
            .andExpect(jsonPath("$.userAddress").value(userDto.getUserAddress()))
            .andExpect(jsonPath("$.userPhone").value(userDto.getUserPhone()))
            .andExpect(jsonPath("$.userAccounts").value(userDto.getUserPhone()))
            .andExpect(jsonPath("$.userRoles").value(userDto.getUserRoles()))
            .andDo(print());

  }


  void testCreateUser() throws Exception {
    CreateUserDto createUserDto = objectMapper.readValue(
            new File("src/main/resources/request/CreateUserDto.json"),
            CreateUserDto.class);
    User user = new User();
    user.setUserEntityId(1L);
    user.setUserPhoto("test.png");
    TransactionMapper.mapDtoToEntity(createUserDto.getProfile(), user);
    TransactionMapper.mapDtoToEntity(createUserDto, user);
//    user.setUserAddress(userAddress);
//    TransactionMapper.mapDtoToEntity(createUserDto.getUserAccounts(), user.getUserAccounts());
//    TransactionMapper.mapDtoToEntity(createUserDto.getUserPhone(), user.getUserPhone());

    addRole(EnumUsers.RoleName.CU, user);
  }
}