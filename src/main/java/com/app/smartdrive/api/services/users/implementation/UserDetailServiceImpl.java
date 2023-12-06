package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserDetail;
import com.app.smartdrive.api.repositories.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByIden(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
    return new UserDetail(user);
  }
}
