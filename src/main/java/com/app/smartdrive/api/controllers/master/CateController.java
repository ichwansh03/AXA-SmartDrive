package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.CateReq;
import com.app.smartdrive.api.dto.master.response.CateRes;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
@CrossOrigin
@Tag(name = "Master Module")
public class CateController implements MasterController<CateReq, Long> {
    private final MasterService cateServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(cateServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(cateServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CateReq request) {
        return new ResponseEntity<>(cateServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CateReq request) {
        return new ResponseEntity<>(cateServiceImpl.update(id, request), HttpStatus.CREATED);
    }
}
