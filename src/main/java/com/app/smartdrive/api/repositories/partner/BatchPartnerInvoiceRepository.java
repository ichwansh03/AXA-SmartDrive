package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchPartnerInvoiceRepository extends JpaRepository<BatchPartnerInvoice, String> {

    List<BatchPartnerInvoice> findAllByStatus(BpinStatus status);

}
