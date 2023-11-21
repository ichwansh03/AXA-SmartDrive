package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.users.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
  
}
