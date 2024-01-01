package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.services.BaseService;
import java.util.List;

public interface BatchPartnerInvoiceService extends BaseService<BatchPartnerInvoice, String> {

    BatchPartnerInvoice createOne(String seroId);
    List<BatchPartnerInvoice> getAllInvoiceNotPaid();
}
