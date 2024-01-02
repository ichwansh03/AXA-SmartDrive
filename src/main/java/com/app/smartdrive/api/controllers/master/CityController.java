package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.CitiesReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/cities")
@Tag(name = "Master Module")
public class CityController implements MasterController<CitiesReq, Long> {
    private final MasterService cityServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(cityServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(cityServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CitiesReq request) {
        return new ResponseEntity<>(cityServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CitiesReq request) {
        cityServiceImpl.getById(id);
        return new ResponseEntity<>(cityServiceImpl.save(request), HttpStatus.CREATED);
    }
}
