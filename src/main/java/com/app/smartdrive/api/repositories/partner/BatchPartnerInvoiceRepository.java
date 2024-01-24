package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchPartnerInvoiceRepository extends JpaRepository<BatchPartnerInvoice, String> {

    List<BatchPartnerInvoice> findAllByStatus(BpinStatus status);

    @Query(value = "SELECT TOP(1) * from partners.batch_partner_invoice WHERE bpin_accountNo=:bpin_accountNo AND bpin_patr_trxno IS NULL", nativeQuery = true)
    BatchPartnerInvoice findByBpinAccountNo(String bpin_accountNo);

    @Query(value = "SELECT * from partners.batch_partner_invoice WHERE bpin_paid_date IS NOT NULL", nativeQuery = true)
    List<BatchPartnerInvoice> findAllBpinStatusPaid();

    @Query(value = "SELECT COUNT(*) from partners.batch_partner_invoice WHERE bpin_paid_date IS NULL " +
            "AND bpin_accountNo=:bpin_accountNo", nativeQuery = true)
    int countBpin(String bpin_accountNo);
}
