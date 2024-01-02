package com.app.smartdrive.api.entities.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthUser implements UserDetails {
  private final User user;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> listRole = user.getUserRoles().stream().map(role -> role.getUserRolesId().getUsroRoleName().getValue()).toList();
    return listRole.stream().map(SimpleGrantedAuthority::new).toList();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
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
