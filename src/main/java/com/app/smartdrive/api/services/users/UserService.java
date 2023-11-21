package com.app.smartdrive.api.services.users;

import java.util.List;

import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.BaseService;

public interface UserService extends BaseService<UserDto,Long>{
  public User save(User user);
}