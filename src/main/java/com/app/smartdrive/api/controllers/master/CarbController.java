package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.CarbReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/carb")
@CrossOrigin
@Tag(name = "Master Module")
public class CarbController implements MasterController<CarbReq, Long> {
    private final MasterService carbServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(carbServiceImpl.getAll(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?>findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(carbServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?>saveData(@Valid @RequestBody CarbReq request) {
        return new ResponseEntity<>(carbServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?>updateData(@PathVariable Long id, @Valid @RequestBody CarbReq request) {
        return new ResponseEntity<>(carbServiceImpl.update(id, request), HttpStatus.CREATED);
    }
}
