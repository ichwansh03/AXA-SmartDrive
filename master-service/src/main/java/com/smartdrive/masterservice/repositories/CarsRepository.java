package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.CarSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends JpaRepository<CarSeries, Long> {
}
