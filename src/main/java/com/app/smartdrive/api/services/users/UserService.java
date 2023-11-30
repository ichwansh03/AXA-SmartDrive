package com.app.smartdrive.api.services.users;

import java.util.List;
import java.util.Optional;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.BaseService;


public interface UserService extends BaseService<User,Long>{
  public User save(User user);
  public User save(CreateUserDto userPost, Long id);
  public User create(CreateUserDto userPost) throws Exception;
  public Optional<User> getUserById(Long id);
  public String loginCu(String identity, String password);
  public String loginEm(String identity, String password);
  public UserDto getByIdDto(Long id);
  public List<UserDto> getAllDto();
}