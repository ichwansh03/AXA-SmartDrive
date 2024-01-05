package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SoWorkorderRepository extends JpaRepository<ServiceOrderWorkorder, Long>, JpaSpecificationExecutor<ServiceOrderWorkorder> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrderWorkorder save(ServiceOrderWorkorder entity);

    List<ServiceOrderWorkorder> findByServiceOrderTasks_SeotId(@Param("seotId") Long seotId);

    @Modifying(clearAutomatically = true)
    @Transactional
    int updateSowoStatus(@Param("sowoStatus") Boolean sowoStatus, @Param("sowoModDate") LocalDateTime sowoModDate, @Param("sowoId") Long sowoId);
}
