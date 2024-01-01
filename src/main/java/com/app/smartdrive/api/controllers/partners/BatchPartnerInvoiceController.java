package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner-invoice")
public class BatchPartnerInvoiceController {

    private final BatchPartnerInvoiceService bpinService;

    @GetMapping("{seroId}")
    public ResponseEntity<BatchPartnerInvoice> createOne(@PathVariable("seroId")String seroId){
        BatchPartnerInvoice batchPartnerInvoice = bpinService.createOne(seroId);
        return ResponseEntity.ok(batchPartnerInvoice);
    }

    @GetMapping
    public ResponseEntity<List<BatchPartnerInvoice>> getAllInvoiceNotPaid(){
        return ResponseEntity.ok(bpinService.getAllInvoiceNotPaid());
    }
}
