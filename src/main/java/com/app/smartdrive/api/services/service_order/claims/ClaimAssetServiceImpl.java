package com.app.smartdrive.api.services.service_order.claims;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequest;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestDto;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestSparePart;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestSparePartDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.CaevRepository;
import com.app.smartdrive.api.repositories.service_orders.CaspRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.services.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimAssetServiceImpl implements ClaimAssetService {

    private final CaevRepository caevRepository;

    private final CaspRepository caspRepository;

    private final PartnerService partnerService;

    private final SoOrderRepository soOrderRepository;
    private final Path caevImageRoot = Paths.get("src/main/resources/images/caev");

    public void init(){
        try {
            Files.createDirectory(caevImageRoot);
        } catch (FileAlreadyExistsException e){
           log.info("File already Exists");
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize images/caev directory");
        }
    }

    @Override
    public ClaimAssetEvidence add(MultipartFile multipartFile, ClaimAssetEvidence caev) throws Exception {

        log.info("ClaimAssetServiceImpl::addAssetEvidence() successfully added {} ",caev);
        return null;
    }

    @Override
    @Transactional
    public List<ClaimAssetEvidence> create(ClaimAssetRequestDto claimAssetRequestDto) throws IOException {
        StringBuilder fileName = new StringBuilder("CAEV-" + claimAssetRequestDto.getServiceOrderId());

        Partner partner = partnerService.getById(claimAssetRequestDto.getPartnerId());
        ServiceOrders serviceOrders = soOrderRepository.findById(claimAssetRequestDto.getServiceOrderId())
                .orElseThrow(()-> new EntityNotFoundException("Service Order not found"));
        List<ClaimAssetRequest> claimAssetRequestList = claimAssetRequestDto.getClaimAssets();

        List<ClaimAssetEvidence> claimAssetEvidenceList = new ArrayList<>();
        for(ClaimAssetRequest request : claimAssetRequestList){
            fileName.append(String.format("-%02d", claimAssetRequestList.indexOf(request)+1))
                    .append(getExtensionFile(Objects.requireNonNull(request.getFile().getOriginalFilename())));
            ClaimAssetEvidence claimAssetEvidence = createOne(request, fileName.toString());
            claimAssetEvidence.setCaevPartners(partner);
            claimAssetEvidence.setCaevServiceOrders(serviceOrders);

            claimAssetEvidenceList.add(claimAssetEvidence);
        }


        return caevRepository.saveAll(claimAssetEvidenceList);
    }

    @Override
    public List<ClaimAssetSparepart> create(ClaimAssetRequestSparePartDto claimAssetRequestDto) throws IOException {
        Partner partner = partnerService.getById(claimAssetRequestDto.getPartnerId());
        ServiceOrders serviceOrders = soOrderRepository.findById(claimAssetRequestDto.getServiceOrderId())
                .orElseThrow(()-> new EntityNotFoundException("Service Order not found"));
        List<ClaimAssetSparepart> caspList = new ArrayList<>();

        for(ClaimAssetRequestSparePart caspRequest : claimAssetRequestDto.getClaimAssets()){
            ClaimAssetSparepart casp = createOne(caspRequest);
            casp.setCaspPartners(partner);
            casp.setCaspServiceOrders(serviceOrders);
            caspList.add(casp);
        }
        return caspRepository.saveAll(caspList);
    }

    public ClaimAssetSparepart createOne(ClaimAssetRequestSparePart caspRequest){
        return ClaimAssetSparepart.builder()
                .caspItemName(caspRequest.getItem())
                .caspItemPrice(caspRequest.getPrice())
                .caspQuantity(caspRequest.getQty())
                .caspSubtotal(caspRequest.getTotalPrice())
                .build();
    }

    private String getExtensionFile(String fileName){
        int dotIndex = fileName.lastIndexOf('.');
        if(dotIndex > 0 && dotIndex < fileName.length()-1){
            return fileName.substring(dotIndex);
        }
        return "";
    }

    public String saveImage(MultipartFile file, String fileName) throws IOException {
        Path target = caevImageRoot.resolve(fileName);
        Files.copy(file.getInputStream(), target);
        return target.toString();
    }

    public ClaimAssetEvidence createOne(ClaimAssetRequest request, String fileName) throws IOException {
        String url = saveImage(request.getFile(), fileName);
        return ClaimAssetEvidence.builder()
                .caevNote(request.getNote())
                .caevFileType(request.getFile().getContentType())
                .caevFileName(fileName)
                .caevFileSize(request.getFile().getSize())
                .caevUrl(url)
                .caevServiceFee(request.getServiceFee())
                .build();
    }
}
