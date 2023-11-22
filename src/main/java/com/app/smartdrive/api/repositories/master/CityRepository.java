package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<Cities, Integer> {
    Optional<Cities> findByCityNameIgnoreCase(String name);
}
