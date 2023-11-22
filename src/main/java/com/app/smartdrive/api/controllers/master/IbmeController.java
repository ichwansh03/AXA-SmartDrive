package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.IbmeDto;
import com.app.smartdrive.api.entities.master.InboxMessaging;
import com.app.smartdrive.api.services.master.implementation.IbmeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/ibme")
public class IbmeController {
    private final IbmeServiceImpl service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping
    public ResponseEntity<?> findAllInboxMessaging() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findInboxMessagingById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveInboxMessaging(@Valid @RequestBody IbmeDto request) {
        InboxMessaging inboxMessaging = new InboxMessaging();
        inboxMessaging.setIbme_type(request.getIbme_type());
        inboxMessaging.setIbme_count(request.getIbme_count());
        inboxMessaging.setIbme_entityid_source(request.getIbme_entityid_source());
        inboxMessaging.setIbme_entityid_target(request.getIbme_entityid_target());
        inboxMessaging.setIbmeDate(LocalDate.parse(request.getIbmeDate().toString(),formatter));
        return ResponseEntity.ok(service.save(inboxMessaging));
    }

    @PutMapping
    public ResponseEntity<?> updateInboxMessaging(@Valid @RequestBody IbmeDto request) {
        InboxMessaging inboxMessaging = service.getById(request.getIbmeId());
        inboxMessaging.setIbme_type(request.getIbme_type());
        inboxMessaging.setIbme_count(request.getIbme_count());
        inboxMessaging.setIbme_entityid_source(request.getIbme_entityid_source());
        inboxMessaging.setIbme_entityid_target(request.getIbme_entityid_target());
        inboxMessaging.setIbmeDate(Objects.requireNonNullElse(request.getIbmeDate(), inboxMessaging.getIbmeDate()));
        return ResponseEntity.ok(service.save(inboxMessaging));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyInboxMessaging(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Inbox Messaging ID : " + id + " Deleted");
    }
}
