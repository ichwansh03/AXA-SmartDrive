package com.smartdrive.partnerservice.controllers;

import com.smartdrive.partnerservice.entities.BatchPartnerInvoice;
import com.smartdrive.partnerservice.services.BatchPartnerInvoiceService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner-invoice")
public class BatchPartnerInvoiceController {

    private final BatchPartnerInvoiceService bpinService;

    @PostMapping
    public ResponseEntity<BatchPartnerInvoice> createOne(@RequestParam(name = "seroId") String seroId){
        BatchPartnerInvoice batchPartnerInvoice = bpinService.createOne(seroId);
        return ResponseEntity.ok(batchPartnerInvoice);
    }

    @GetMapping
    public ResponseEntity<List<BatchPartnerInvoice>> getAllInvoiceNotPaid(){
        return ResponseEntity.ok(bpinService.getAllInvoiceNotPaid());
    }
}
