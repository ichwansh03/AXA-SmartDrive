package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabrRepository extends JpaRepository<CarBrand, Integer> {
}
