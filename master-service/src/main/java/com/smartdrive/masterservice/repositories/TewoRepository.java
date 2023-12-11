package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.TemplateTaskWorkOrder;
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
