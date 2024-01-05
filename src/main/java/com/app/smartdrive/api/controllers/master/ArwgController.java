package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.ArwgReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/arwg")
@Tag(name = "Master Module", description = "This Tab Contains All Operation for Master Module")
public class ArwgController implements MasterController<ArwgReq, String> {
    private final MasterService arwgServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(arwgServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        return ResponseEntity.ok(arwgServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ArwgReq request) {
        return new ResponseEntity<>(arwgServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable String id,@Valid @RequestBody ArwgReq request) {
        arwgServiceImpl.getById(id);
        return new ResponseEntity<>(arwgServiceImpl.save(request), HttpStatus.CREATED);
    }
}
