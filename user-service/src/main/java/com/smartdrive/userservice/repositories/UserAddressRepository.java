package com.smartdrive.userservice.repositories;

import com.smartdrive.userservice.entities.UserAddress;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    @Query(value = "SELECT TOP(1) * FROM USERS.USER_ADDRESS ORDER BY usdr_id DESC",
            nativeQuery = true)
    Optional<UserAddress> findLastOptional();

    @Query(value = "SELECT * FROM USERS.USER_ADDRESS WHERE USDR_ID = ?1 AND USDR_ENTITYID = ?2", nativeQuery = true)
    Optional<UserAddress> findUserAddressOptional(Long id, Long userId);

    UserAddress findByUserUserEntityId(Long userEntityId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM USERS.USER_ADDRESS WHERE USDR_ID = ?1", nativeQuery = true)
    int deleteAddress(Long id);
}
