package com.app.smartdrive.api.dto.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.app.smartdrive.api.entities.users.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
  private Long userEntityId;
  private String userName;
  private String userFullName;
  private String userBirthPlace;
  private LocalDateTime userBirthDate;
  private Collection<? extends GrantedAuthority> roles;
  private String accessToken;
  private String tokenType;
}
