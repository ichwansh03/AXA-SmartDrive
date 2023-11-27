package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.BaseService;


public interface UserService extends BaseService<UserDto,Long>{
  public List<UserDto> getAll();
  public User save(User user);
  public User save(CreateUserDto userPost, Long id);
  public User create(CreateUserDto userPost);
}