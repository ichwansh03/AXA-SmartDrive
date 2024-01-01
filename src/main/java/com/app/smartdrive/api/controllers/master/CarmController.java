package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.CarmReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/carm")
@Tag(name = "Master Module")
public class CarmController implements MasterController<CarmReq, Long> {
    private final MasterService carmServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(carmServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(carmServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarmReq request) {
        return new ResponseEntity<>(carmServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CarmReq request) {
        carmServiceImpl.getById(id);
        return new ResponseEntity<>(carmServiceImpl.save(request), HttpStatus.CREATED);
    }
}
