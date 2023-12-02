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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        List<IbmeDto> result = TransactionMapper.mapEntityListToDtoList(service.getAll(), IbmeDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), IbmeDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody IbmeDto request) {
        return getResponseEntity(request, new InboxMessaging());
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody IbmeDto request) {
        return getResponseEntity(request, service.getById(request.getIbmeId()));
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody @Valid IbmeDto request, InboxMessaging result) {
        if(request.getIbmeDate() != null) {
            LocalDate localDate = LocalDate.parse(request.getIbmeDate().toString(), formatter);
            result.setIbmeDate(localDate);
        }

        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyData(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Inbox Messaging Was Deleted !");
    }
}
