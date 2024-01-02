package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.entities.users.AuthUser;
import com.app.smartdrive.api.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
            .findUserByIden(username)
            .map(user -> new AuthUser(user))
            .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
  }
}
