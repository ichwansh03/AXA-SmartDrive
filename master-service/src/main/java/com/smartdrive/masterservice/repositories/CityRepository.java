package com.smartdrive.masterservice.repositories;

import java.util.List;

import com.smartdrive.masterservice.entities.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CityRepository extends JpaRepository<Cities, Long> {
    @Query(name = "SELECT * FROM MTR.CITIES WHERE CITY_NAME = ?1", nativeQuery = true)
    Cities findByCityName(String cityName);

    List<Cities> findByCityNameIgnoreCase(String searchTerm);
}
