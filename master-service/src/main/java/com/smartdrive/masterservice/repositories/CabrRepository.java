package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabrRepository extends JpaRepository<CarBrand, Long> {
}
