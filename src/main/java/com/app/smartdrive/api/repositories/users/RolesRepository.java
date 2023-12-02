package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;

@Repository
public interface RolesRepository extends JpaRepository<Roles, RoleName>{
}
