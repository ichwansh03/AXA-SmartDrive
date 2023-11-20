package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarmRepository extends JpaRepository<CarModel, Long> {
}
