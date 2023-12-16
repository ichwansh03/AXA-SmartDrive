package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RequiredArgsConstructor
public class RequestBuilder {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  ResultActions getUserById(Long id) throws  Exception{
    return mockMvc.perform(get("/user/{id}", id));
  }

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
}
