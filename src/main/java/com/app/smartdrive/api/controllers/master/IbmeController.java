package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.IbmeReq;
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
@RequestMapping("/master/ibme")
@CrossOrigin
@Tag(name = "Master Module")
public class IbmeController implements MasterController<IbmeReq, Long> {
    private final MasterService ibmeServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(ibmeServiceImpl.getAll(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(ibmeServiceImpl.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody IbmeReq request) {
        return new ResponseEntity<>(ibmeServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody IbmeReq request) {
        ibmeServiceImpl.getById(id);
        return ResponseEntity.ok(ibmeServiceImpl.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroyData(@PathVariable Long id) {
        ibmeServiceImpl.deleteById(id);
        return ResponseEntity.ok("Inbox Messaging Was Deleted !");
    }
}
