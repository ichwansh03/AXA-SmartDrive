package com.smartdrive.serviceorderservice.repositories;

import com.smartdrive.serviceorderservice.entities.ServiceOrderWorkorder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SoWorkorderRepository extends JpaRepository<ServiceOrderWorkorder, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrderWorkorder save(ServiceOrderWorkorder entity);

    List<ServiceOrderWorkorder> findSowoBySeotId(@Param("seotId") Long seotId);

    @Modifying(clearAutomatically = true)
    @Transactional
    int updateSowoStatus(@Param("sowoStatus") Boolean sowoStatus, @Param("sowoModDate") LocalDateTime sowoModDate, @Param("sowoId") Long sowoId);
}
