package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchPartnerInvoiceRepository extends JpaRepository<BatchPartnerInvoice, String> {

    List<BatchPartnerInvoice> findAllByStatus(BpinStatus status);

    @Query(value = "SELECT * from partners.batch_partner_invoice WHERE bpin_accountNo=:bpin_accountNo", nativeQuery = true)
    BatchPartnerInvoice findByBpinAccountNo(String bpin_accountNo);
}
