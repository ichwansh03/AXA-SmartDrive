package com.app.smartdrive.api.services.users;

import java.util.List;
import java.util.Optional;
import com.app.smartdrive.api.dto.user.ProfileDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.services.BaseService;


public interface UserService extends BaseService<User, Long> {
  public User save(User user);

  public UpdateUserRequestDto save(UpdateUserRequestDto userPost, Long id);

  public User createUserCustomer(CreateUserDto userPost);

  public User createUser(ProfileRequestDto userPost);

  public Optional<User> getUserById(Long id);

  public String loginCu(String identity, String password);

  public String loginEm(String identity, String password);

  public UserDto getByIdDto(Long id);

  public List<UserDto> getAllDto();
}
