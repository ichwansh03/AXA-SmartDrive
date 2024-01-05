package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.TestaReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/testa")
@Tag(name = "Master Module")
public class TestaController implements MasterController<TestaReq, Long> {
    private final MasterService testaServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(testaServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(testaServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TestaReq request) {
        return new ResponseEntity<>(testaServiceImpl.save(request), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody TestaReq request) {
        testaServiceImpl.getById(id);
        return new ResponseEntity<>(testaServiceImpl.save(request), HttpStatus.OK);
    }
}
