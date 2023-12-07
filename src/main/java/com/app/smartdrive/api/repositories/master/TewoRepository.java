package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TewoRepository extends JpaRepository<TemplateTaskWorkOrder, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<TemplateTaskWorkOrder> findByTewoTestaId(Long id);
}
