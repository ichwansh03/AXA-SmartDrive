package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.CarBrandDto;
import com.app.smartdrive.api.dto.master.CarModelDto;
import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.entities.master.CarModel;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.services.master.implementation.CarbServiceImpl;
import com.app.smartdrive.api.services.master.implementation.CarmServiceImpl;
import com.app.smartdrive.api.services.master.implementation.CarsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/cars")
public class CarsController {
    private final CarbServiceImpl carbService;
    private final CarsServiceImpl carsService;
    private final CarmServiceImpl carmService;

    @GetMapping("/brands")
    public ResponseEntity<?> findAllCars() {
        return ResponseEntity.ok(carbService.findAllCarBrand());
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<?> findCarBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(carbService.findCarBrandById(id));
    }

    @GetMapping("/models")
    public ResponseEntity<?> findAllCarModels() {
        return ResponseEntity.ok(carmService.findAllCarModel());
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<?> findCarModelById(@PathVariable Long id) {
        return ResponseEntity.ok(carmService.findCarModelById(id));
    }

    @GetMapping("/series")
    public ResponseEntity<?> findAllCarSeries() {
        return ResponseEntity.ok(carsService.findAllCarSeries());
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<?> findCarSeriesById(@PathVariable Long id) {
        return ResponseEntity.ok(carsService.findCarSeriesById(id));
    }

    @PostMapping("/brands")
    public ResponseEntity<?> saveBrands(@Valid @RequestBody CarBrandDto carBrandDto){
        CarBrand carBrand = new CarBrand();
        carBrand.setCabrName(carBrandDto.getCabrName());
        return ResponseEntity.ok(carbService.createBrand(carBrand));
    }

    @PostMapping("/models")
    public ResponseEntity<?> saveModels(@Valid @RequestBody CarModelDto carModelDto){
        CarModel carModel = new CarModel();
        carbService.findCarBrandById(carModelDto.getCarmCarbId());
        carModel.setCarmName(carModelDto.getCarmName());
        carModel.setCarmCarbId(carModelDto.getCarmCarbId());
        return ResponseEntity.ok(carmService.createModel(carModel));
    }

    @PostMapping("/series")
    public ResponseEntity<?> saveSeries(@Valid @RequestBody CarSeriesDto carSeriesDto){
        CarSeries carSeries = new CarSeries();
        carmService.findCarModelById(carSeriesDto.getCarsCarmId());
        carSeries.setCarsName(carSeriesDto.getCarsName());
        carSeries.setCarsCarmId(carSeriesDto.getCarsCarmId());
        return ResponseEntity.ok(carsService.createSeries(carSeries));
    }

    @PutMapping("/brands/update")
    public ResponseEntity<?> updateBrandById(@Valid @RequestBody CarBrandDto carBrandDto) {
        carbService.findCarBrandById(carBrandDto.getCabrID());
        CarBrand carBrand = new CarBrand();
        carBrand.setCabrID(carBrandDto.getCabrID());
        carBrand.setCabrName(carBrandDto.getCabrName());
        return ResponseEntity.ok(carbService.createBrand(carBrand));
    }
    @PutMapping("/models/update")
    public ResponseEntity<?> updateModelById(@Valid @RequestBody CarModelDto carModelDto) {
        carmService.findCarModelById(carModelDto.getCarmId());
        CarModel carModel = new CarModel();
        carModel.setCarmId(carModelDto.getCarmId());
        carModel.setCarmName(carModelDto.getCarmName());
        return ResponseEntity.ok(carmService.createModel(carModel));
    }
    @PutMapping("/series/update")
    public ResponseEntity<?> updateSeriesById(@Valid @RequestBody CarSeriesDto carSeriesDto) {
        carsService.findCarSeriesById(carSeriesDto.getCarsId());
        CarSeries carSeries = new CarSeries();
        carSeries.setCarsId(carSeries.getCarsId());
        carSeries.setCarsName(carSeries.getCarsName());
        return ResponseEntity.ok(carsService.createSeries(carSeries));
    }
}
