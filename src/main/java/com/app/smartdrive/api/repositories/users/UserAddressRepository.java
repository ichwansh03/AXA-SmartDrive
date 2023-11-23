package com.app.smartdrive.api.repositories.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UserAdressId> {
    @Query(value = "SELECT TOP(1) * FROM USERS.USER_ADDRESS ORDER BY usdr_id DESC", nativeQuery = true)
    Optional<UserAddress> findLastOptional();
    UserAddress findByUserUserEntityId(Long userEntityId);
}
