package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.repositories.service_orders.CaevRepository;
import com.app.smartdrive.api.services.service_order.ClaimAssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimAssetServiceImpl implements ClaimAssetService {

    private final CaevRepository caevRepository;

    @Override
    public ClaimAssetEvidence addAssetEvidence(MultipartFile multipartFile, ClaimAssetEvidence caev) throws Exception {

        log.info("ClaimAssetServiceImpl::addAssetEvidence() successfully added {} ",caev);
        return null;
    }
}
