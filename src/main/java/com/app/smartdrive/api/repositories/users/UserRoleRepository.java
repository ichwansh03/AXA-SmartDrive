package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;

public interface UserRoleRepository extends JpaRepository<UserRoles, UserRolesId>{
  
}
