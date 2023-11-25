package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.repositories.service_orders.CaevRepository;
import com.app.smartdrive.api.services.service_order.CaevService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaevServiceImpl implements CaevService {

    private final CaevRepository caevRepository;

    @Override
    public List<ClaimAssetEvidence> findAllByCaevSeroId(Long seroId) {
        return caevRepository.findAllByCaevSeroId(seroId);
    }

    @Override
    public ClaimAssetEvidence findById(Long caevId) {
        return caevRepository.findById(caevId).get();
    }

    @Override
    public List<ClaimAssetEvidence> findAllCaev() {
        return caevRepository.findAll();
    }
}
