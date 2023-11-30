package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.Cities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CityRepository extends JpaRepository<Cities, Long> {
    @Query(name = "SELECT * FROM MTR.CITIES WHERE CITY_NAME = ?1", nativeQuery = true)
    Cities findByCityName(String cityName);

    List<Cities> findByCityNameIgnoreCase(String searchTerm);
}
