package com.smartdrive.partnerservice.services;

import com.smartdrive.partnerservice.entities.BatchPartnerInvoice;

import java.util.List;

public interface BatchPartnerInvoiceService extends BaseService<BatchPartnerInvoice, String> {
    List<BatchPartnerInvoice> generateBpin();
    BatchPartnerInvoice createOne(String seroId);

    List<BatchPartnerInvoice> getAllInvoiceNotPaid();
}
