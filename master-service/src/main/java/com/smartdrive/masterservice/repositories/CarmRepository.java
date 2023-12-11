package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarmRepository extends JpaRepository<CarModel, Long> {
}
