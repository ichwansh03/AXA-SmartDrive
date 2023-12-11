package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TetyRepository extends JpaRepository<TemplateType, Long> {
}
