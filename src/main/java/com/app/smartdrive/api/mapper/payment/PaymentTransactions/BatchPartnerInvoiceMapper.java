//package com.app.smartdrive.api.mapper.payment.PaymentTransactions;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsByUserDtoRequests;
//import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
//import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
//
//import lombok.Data;
//
//@Data
//
//public class BatchPartnerInvoiceMapper {
//    private final BatchPartnerInvoiceService serviceInvoicePartner;
//
//    public BatchPartnerInvoiceMapper(BatchPartnerInvoiceService serviceInvoicePartner) {
//        this.serviceInvoicePartner = serviceInvoicePartner;
//    }
//    public List<BatchPartnerInvoice> getAllUnpaidInvoices() {
//        return serviceInvoicePartner.getAllInvoiceNotPaid();
//    }
//
//
//    public BatchPartnerInvoiceResponse mapperBatchPartnerInvoiceDto(TransactionsByUserDtoRequests requests){
//        List<BatchPartnerInvoice> listPartnerInvoice = getAllUnpaidInvoices();
//        BatchPartnerInvoiceResponse dto = null;
//
//        for (BatchPartnerInvoice batchPartnerInvoice : listPartnerInvoice) {
//            if(requests.getUsac_accountno().equals(batchPartnerInvoice.getAccountNo())){
//                dto = BatchPartnerInvoiceResponse.builder()
//                .accountNo(batchPartnerInvoice.getAccountNo())
//                .createdOn(LocalDateTime.now())
//                .subTotal(batchPartnerInvoice.getSubTotal())
//                .tax(batchPartnerInvoice.getTax())
//                .status(batchPartnerInvoice.getStatus())
//                .paidDate(LocalDateTime.now())
//                .partner(batchPartnerInvoice.getPartner())
//                .paymentTransactions(batchPartnerInvoice.getPaymentTransactions())
//                .service_orders(batchPartnerInvoice.getServiceOrders())
//                .build();
//
//            }
//
//        }
//        return dto;
//    }
//}
