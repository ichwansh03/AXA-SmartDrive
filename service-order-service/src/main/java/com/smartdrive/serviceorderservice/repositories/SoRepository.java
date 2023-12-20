package com.smartdrive.serviceorderservice.repositories;

import com.smartdrive.serviceorderservice.entities.Services;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface SoRepository extends JpaRepository<Services, Long> {

    //execute one by one data simultaneously when data changed concurrent
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    Services save(Services services);

}
