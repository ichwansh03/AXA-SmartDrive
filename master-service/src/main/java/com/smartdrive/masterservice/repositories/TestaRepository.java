package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.TemplateServiceTask;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestaRepository extends JpaRepository<TemplateServiceTask, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<TemplateServiceTask> findByTestaTetyId(Long testaTetyId);
}
