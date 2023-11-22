package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.Cities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CityRepository extends JpaRepository<Cities, Long> {
    Cities findByCityName(String cityName);

}
