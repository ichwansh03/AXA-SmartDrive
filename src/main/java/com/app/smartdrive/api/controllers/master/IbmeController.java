package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.IbmeDto;
import com.app.smartdrive.api.entities.master.InboxMessaging;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.IbmeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/ibme")
@Tag(name = "Master Module")
public class IbmeController implements BaseController<IbmeDto, Long> {
    private final IbmeService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody IbmeDto request) {
        InboxMessaging result = new InboxMessaging();
        return getResponseEntity(request, result);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody IbmeDto request) {
        InboxMessaging result = service.getById(request.getIbmeId());
        return getResponseEntity(request, result);
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody @Valid IbmeDto request, InboxMessaging result) {
        result = TransactionMapper.mapDtoToEntity(request, result);
        if(request.getIbmeDate() != null) {
            LocalDate localDate = LocalDate.parse(request.getIbmeDate().toString(), formatter);
            result.setIbmeDate(localDate);
        }

        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyData(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Inbox Messaging Was Deleted !");
    }
}
