package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchPartnerInvoiceServiceImpl implements BatchPartnerInvoiceService {

    private final ServOrderService servOrderService;
    private final BatchPartnerInvoiceRepository bpinRepository;

    private final CustomerClaimService customerClaimService;

    public BatchPartnerInvoice save(BatchPartnerInvoice entity) {
        return null;
    }


    public void deleteById(String s) {
        bpinRepository.deleteById(s);
    }

    @Override
    public BatchPartnerInvoice createOne(String seroId) {
        ServiceOrders serviceOrders = servOrderService.findServiceOrdersById(seroId);
        double paid = calculatePaidFromCaev(serviceOrders.getClaimAssetEvidence()) + calculatePaidFromCasp(serviceOrders.getClaimAssetSparepart());
        BatchPartnerInvoice batchPartnerInvoice = BatchPartnerInvoice.builder()
                .accountNo(serviceOrders.getPartner().getPartAccountNo())
                .partner(serviceOrders.getPartner())
                .serviceOrders(serviceOrders)
                .subTotal(paid)
                .tax(paid*(0.1))
                .no(generateInvoiceNo(seroId))
                .build();


        CustomerRequest customerRequest = serviceOrders.getServices().getCustomer();
        this.customerClaimService.calculateSubtotalAndEventPrice(customerRequest, paid, batchPartnerInvoice.getTax());

        return bpinRepository.save(batchPartnerInvoice);
    }

    @Override
    public List<BatchPartnerInvoice> getAllInvoiceNotPaid() {
        return bpinRepository.findAllByStatus(BpinStatus.NOT_PAID);
    }


    public String generateInvoiceNo(String seroId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "INV"+LocalDateTime.now().format(formatter)+"-"+seroId.split("-")[0];
    }

    public Double calculatePaidFromCaev(List<ClaimAssetEvidence> claimAssetEvidenceList){
        return claimAssetEvidenceList.stream()
                .map(ClaimAssetEvidence::getCaevServiceFee)
                .reduce(0.0, (Double::sum));
    }

    public Double calculatePaidFromCasp(List<ClaimAssetSparepart> claimAssetSparepartList){
        return claimAssetSparepartList.stream()
                .map(ClaimAssetSparepart::getCaspSubtotal)
                .reduce(0.0, ((a,b)-> (a+b)));
    }

}
