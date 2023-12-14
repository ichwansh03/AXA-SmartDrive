package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemiRepository extends JpaRepository<ServicePremi, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServicePremi save(ServicePremi entity);

    @Modifying(clearAutomatically = true)
    int updateSemiStatus(@Param("semiStatus") String semiStatus, @Param("semiServId") Long semiServId);

}
