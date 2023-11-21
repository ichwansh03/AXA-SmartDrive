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

    @GetMapping("/models")
    public ResponseEntity<?> findAllCarModels() {
        return ResponseEntity.ok(carmService.findAllCarModel());
    }

    @GetMapping("/series")
    public ResponseEntity<?> findAllCarSeries() {
        return ResponseEntity.ok(carsService.findAllCarSeries());
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<?> findCarBrandById(@PathVariable Long id) {
        CarBrand carBrand = carbService.findCarBrandById(id);
        CarBrandDto carBrandDto = new CarBrandDto(
                carBrand.getCabrID(),
                carBrand.getCabrName()
        );
        return ResponseEntity.ok(carBrandDto);
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<?> findCarModelById(@PathVariable Long id) {
        CarModel carModel = carmService.findCarModelById(id);
        CarModelDto carModelDto = new CarModelDto(
                carModel.getCarmId(),
                carModel.getCarmName(),
                carModel.getCarmCarbId()
        );
        return ResponseEntity.ok(carModelDto);
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<?> findCarSeriesById(@PathVariable Long id) {
        CarSeries carSeries = carsService.findCarSeriesById(id);
        CarSeriesDto carSeriesDto = new CarSeriesDto(
                carSeries.getCarsId(),
                carSeries.getCarsName(),
                carSeries.getCarsPassenger(),
                carSeries.getCarsCarmId()
        );
        return ResponseEntity.ok(carSeriesDto);
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
        CarBrand carBrand =  carbService.findCarBrandById(carBrandDto.getCabrID());
        carBrand.setCabrName(carBrandDto.getCabrName());
        return ResponseEntity.ok(carbService.createBrand(carBrand));
    }

    @PutMapping("/models/update")
    public ResponseEntity<?> updateModelById(@Valid @RequestBody CarModelDto carModelDto) {
        CarModel carModel = carmService.findCarModelById(carModelDto.getCarmId());
        carModel.setCarmName(carModelDto.getCarmName());
        carModel.setCarmCarbId(carModelDto.getCarmCarbId());
        return ResponseEntity.ok(carmService.createModel(carModel));
    }

    @PutMapping("/series/update")
    public ResponseEntity<?> updateSeriesById(@Valid @RequestBody CarSeriesDto carSeriesDto) {
        CarSeries carSeries = carsService.findCarSeriesById(carSeriesDto.getCarsId());
        carSeries.setCarsName(carSeries.getCarsName());
        carSeries.setCarsCarmId(carSeries.getCarsCarmId());
        return ResponseEntity.ok(carsService.createSeries(carSeries));
    }
}
