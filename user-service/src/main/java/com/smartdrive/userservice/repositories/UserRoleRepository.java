package com.smartdrive.userservice.repositories;

import com.smartdrive.userservice.entities.UserPhoneId;
import com.smartdrive.userservice.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoles, UserPhoneId>{
    @Modifying
    @Query(value = """
            UPDATE users.user_roles
            SET usro_role_name = 'CU'
            WHERE usro_entityid = ?1
            AND usro_role_name = 'PC'""", nativeQuery = true)
    void updateUserRoleTOCu(Long usro_entityId);

    @Query(value = """
            SELECT TOP(1) * FROM users.user_roles
            WHERE usro_entityid = ?1\s
            AND usro_role_name = 'PC'""", nativeQuery = true)
    Optional<UserRoles> checkRolePcIsExist(Long usro_entityId);
}
