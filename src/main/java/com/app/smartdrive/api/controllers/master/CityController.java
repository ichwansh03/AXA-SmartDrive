package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.services.master.implementation.CityServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/cities")
public class CityController {
    private final CityServiceImpl service;

    @GetMapping
    public ResponseEntity<?> findAllCities() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Cities cities = service.getById(id);
        CitiesDto citiesDto = new CitiesDto();
        citiesDto.setCityId(cities.getCityId());
        citiesDto.setCityName(cities.getCityName());
        citiesDto.setCityProvId(cities.getCityProvId());
        return ResponseEntity.ok(citiesDto);
    }

    @PostMapping
    public ResponseEntity<?> saveCity(@Valid @RequestBody CitiesDto request) {
        Cities cities = new Cities();
        cities.setCityName(request.getCityName());
        cities.setCityProvId(request.getCityProvId());
        return ResponseEntity.ok(service.save(cities));
    }

    @PutMapping
    public ResponseEntity<?> updateCity(@Valid @RequestBody CitiesDto request) {
        Cities cities = service.getById(request.getCityId());
        cities.setCityName(request.getCityName());
        return ResponseEntity.ok(service.save(cities));
    }
}
