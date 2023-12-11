package com.smartdrive.masterservice.controllers;

import com.smartdrive.masterservice.dto.request.EmailReq;
import com.smartdrive.masterservice.dto.request.IbmeReq;
import com.smartdrive.masterservice.dto.request.NotificationReq;
import com.smartdrive.masterservice.dto.response.IbmeRes;
import com.smartdrive.masterservice.entities.InboxMessaging;
import com.smartdrive.masterservice.mapper.TransactionMapper;
import com.smartdrive.masterservice.services.EmailService;
import com.smartdrive.masterservice.services.IbmeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class IbmeController implements BaseController<IbmeReq, Long> {
    private final IbmeService service;
    private final EmailService emailService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<IbmeRes> result = TransactionMapper.mapEntityListToDtoList(service.getAll(), IbmeRes.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), IbmeRes.class));
    }

    @Override
    public ResponseEntity<?> saveData(@Valid @RequestBody IbmeReq request) {
        return ResponseEntity.ok("OK");
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> saveAndSendMail(@Valid @RequestBody NotificationReq request) {
        IbmeReq ibmeReq = new IbmeReq();
        ibmeReq.setIbmeDate(request.getIbmeDate());
        ibmeReq.setIbme_type(request.getIbme_type());
        ibmeReq.setIbme_entityid_target(request.getIbme_entityid_target());
        ibmeReq.setIbme_entityid_source(request.getIbme_entityid_source());
        ibmeReq.setIbme_count(request.getIbme_count());
        getResponseEntity(ibmeReq, new InboxMessaging());

        EmailReq emailReq = new EmailReq();
        emailReq.setTo(request.getTargetMail());
        emailReq.setSubject(request.getSubjectMail());
        emailReq.setBody(request.getBodyMail());
        emailService.sendMail(emailReq);

        return ResponseEntity.ok("Email Has Been Sent");
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody IbmeReq request) {
        InboxMessaging result = getResponseEntity(request, service.getById(id));
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(result, IbmeRes.class));
    }

    private InboxMessaging getResponseEntity(@RequestBody @Valid IbmeReq request, InboxMessaging result) {
        if(request.getIbmeDate() != null) {
            LocalDate localDate = LocalDate.parse(request.getIbmeDate().toString(), formatter);
            result.setIbmeDate(localDate);
        }

        return service.save(TransactionMapper.mapDtoToEntity(request, result));
    }

    @Override
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyData(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Inbox Messaging Was Deleted !");
    }
}
