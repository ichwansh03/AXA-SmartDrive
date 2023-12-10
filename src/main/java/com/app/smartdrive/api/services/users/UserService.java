package com.app.smartdrive.api.services.users;

import java.util.List;
import java.util.Optional;
import com.app.smartdrive.api.dto.user.ProfileDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.services.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends BaseService<User, Long> {
  public User save(User user);

  public User createUserCustomer(CreateUserDto userPost);

  public User createUser(ProfileRequestDto userPost);

  @Transactional
  UpdateUserRequestDto updateUser(UpdateUserRequestDto userPost, Long id);

  public Optional<User> getUserById(Long id);

  public String changePassword(Long id, PasswordRequestDto passwordRequestDto);

  public UserDetailsService userDetailsService();
}
