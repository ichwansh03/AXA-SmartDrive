package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UserAdressId> {
  
    UserAddress findByUserUserEntityId(Long userEntityId);
}
