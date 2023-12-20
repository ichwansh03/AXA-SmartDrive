package com.smartdrive.userservice.repositories;

import com.smartdrive.userservice.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartdrive.userservice.entities.EnumUsers.RoleName;

@Repository
public interface RolesRepository extends JpaRepository<Roles, RoleName> {
}
