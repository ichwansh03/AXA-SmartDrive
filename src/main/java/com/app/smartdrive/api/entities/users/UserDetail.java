package com.app.smartdrive.api.entities.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserDetail implements UserDetails {
  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> listRole = user.getUserRoles().stream().map(role -> role.getUserRolesId().getUsroRoleName().getValue()).toList();
    List<SimpleGrantedAuthority> authorities = listRole.stream().map(SimpleGrantedAuthority::new).toList();
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getUserPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
