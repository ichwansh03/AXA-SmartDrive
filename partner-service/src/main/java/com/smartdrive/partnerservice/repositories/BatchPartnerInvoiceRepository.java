package com.smartdrive.partnerservice.repositories;

import com.smartdrive.partnerservice.entities.BatchPartnerInvoice;
import com.smartdrive.partnerservice.entities.enums.BpinStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchPartnerInvoiceRepository extends JpaRepository<BatchPartnerInvoice, String> {
    List<BatchPartnerInvoice> findAllByStatus(BpinStatus status);
}
