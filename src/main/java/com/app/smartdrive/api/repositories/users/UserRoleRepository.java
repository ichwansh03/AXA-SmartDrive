package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoles, UserRolesId>{
    @Modifying
    @Query(value = "UPDATE users.user_roles\n" +
            "SET usro_role_name = 'CU'\n" +
            "WHERE usro_entityid = ?1\n" +
            "AND usro_role_name = 'PC'", nativeQuery = true)
    void updateUserRoleTOCu(Long usro_entityId);

    @Query(value = "SELECT TOP(1) * FROM users.user_roles\n" +
            "WHERE usro_entityid = ?1 \n" +
            "AND usro_role_name = 'PC'", nativeQuery = true)
    Optional<UserRoles> checkRolePcIsExist(Long usro_entityId);

    @Query(value = "SELECT TOP(1) * FROM users.user_roles\n" +
            "WHERE usro_entityid = ?1 \n" +
            "AND usro_role_name = ?2", nativeQuery = true)
    Optional<UserRoles> checkUserRoleIsExist(Long usro_entityId, String roleName);
}
