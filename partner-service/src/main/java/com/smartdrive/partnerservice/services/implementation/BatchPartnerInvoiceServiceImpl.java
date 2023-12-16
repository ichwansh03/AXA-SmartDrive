package com.smartdrive.partnerservice.services.implementation;

import com.smartdrive.partnerservice.entities.BatchPartnerInvoice;
import com.smartdrive.partnerservice.entities.enums.BpinStatus;
import com.smartdrive.partnerservice.repositories.BatchPartnerInvoiceRepository;
import com.smartdrive.partnerservice.services.BatchPartnerInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchPartnerInvoiceServiceImpl implements BatchPartnerInvoiceService {

//    private final ServOrderService servOrderService;
    private final BatchPartnerInvoiceRepository bpinRepository;

    @Override
    public BatchPartnerInvoice getById(String s) {
        return null;
    }

    @Override
    public List<BatchPartnerInvoice> getAll() {
        return null;
    }

    @Override
    public BatchPartnerInvoice save(BatchPartnerInvoice entity) {
        return null;
    }

    @Override
    public void deleteById(String s) {
        BatchPartnerInvoiceService.super.deleteById(s);
    }

    @Override
    public List<BatchPartnerInvoice> generateBpin() {
        return null;
    }

    @Override
    public BatchPartnerInvoice createOne(String seroId) {
//        ServiceOrders serviceOrders = servOrderService.findServiceOrdersById(seroId);
//        double paid = calculatePaidFromCaev(serviceOrders.getClaimAssetEvidence()) + calculatePaidFromCasp(serviceOrders.getClaimAssetSparepart());
//        BatchPartnerInvoice batchPartnerInvoice = BatchPartnerInvoice.builder()
//                .accountNo(serviceOrders.getPartner().getPartAccountNo())
//                .partner(serviceOrders.getPartner())
//                .serviceOrders(serviceOrders)
//                .subTotal(paid)
//                .tax(paid*(0.1))
//                .no(generateInvoiceNo(seroId))
//                .build();
//
//
//        return bpinRepository.save(batchPartnerInvoice);
        return null;
    }

    @Override
    public List<BatchPartnerInvoice> getAllInvoiceNotPaid() {
        return bpinRepository.findAllByStatus(BpinStatus.NOT_PAID);
    }


    public String generateInvoiceNo(String seroId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "INV"+LocalDateTime.now().format(formatter)+"-"+seroId.split("-")[0];
    }

//    public Double calculatePaidFromCaev(List<ClaimAssetEvidence> claimAssetEvidenceList){
//        return claimAssetEvidenceList.stream()
//                .map(ClaimAssetEvidence::getCaevServiceFee)
//                .reduce(0.0, (Double::sum));
//    }
    public Double calculatePaidFromCaev(List<Double> claimAssetEvidenceList){
        return 0.0;
    }

//    public Double calculatePaidFromCasp(List<ClaimAssetSparepart> claimAssetSparepartList){
//        return claimAssetSparepartList.stream()
//                .map(ClaimAssetSparepart::getCaspSubtotal)
//                .reduce(0.0, ((a,b)-> (a+b)));
//    }

    public Double calculatePaidFromCasp(List<Double> claimAssetSparepartList){
        return 0.0;
    }

}
