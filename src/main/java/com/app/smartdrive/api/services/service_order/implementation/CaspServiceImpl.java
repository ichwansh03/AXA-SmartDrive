package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.repositories.service_orders.CaspRepository;
import com.app.smartdrive.api.services.service_order.CaspService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaspServiceImpl implements CaspService {

    private final CaspRepository caspRepository;

    @Override
    public ClaimAssetSparepart findCaspById(Long caspId) {
        return caspRepository.findById(caspId).get();
    }

    @Override
    public List<ClaimAssetSparepart> findAllCasp() {
        return caspRepository.findAll();
    }

    @Override
    public List<ClaimAssetSparepart> findAllByCaspSeroId(Long seroId) {
        return caspRepository.findAllByCaspSeroId(seroId);
    }
}
