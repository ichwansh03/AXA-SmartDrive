package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepo;
import com.app.smartdrive.api.services.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final CityRepository cityRepository;
    @Override
    @Transactional
    public Partner getById(Long entityId) {
        return partnerRepository.findById(entityId).orElseThrow(()->new RuntimeException());
    }

    @Override
    @Transactional
    public List<Partner> getAll() {
        return partnerRepository.findAll();
    }

    @Override
    @Transactional
    public Partner save(Partner entity) {
        if(Objects.isNull(entity.getBusinessEntity())){
            BusinessEntity businessEntity = new BusinessEntity();
            entity.setBusinessEntity(businessEntity);
        }
        entity.getBusinessEntity().setEntityModifiedDate(LocalDateTime.now());
        return partnerRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        partnerRepository.deleteById(id);

    }

    @Override
    public Partner convertToEntity(PartnerRequest request) {
        Cities city = cityRepository.findByCityNameIgnoreCase(request.getCity()).orElseThrow(()->new RuntimeException("city not found for name " + request.getCity()));
        Partner partner = new Partner();
        partner.setPartName(request.getName());
        partner.setPartAddress(request.getAddress());
        partner.setPartNpwp(request.getNpwp());
        partner.setCity(city);
        return partner;
    }

    @Override
    @Transactional
    public Page<Partner> searchByNameOrNpwp(String value, int page) {
        Pageable pageable = PageRequest.of(page, 1);
        String valueWithBound = "%"+value+"%";
        Page<Partner> partnerWithNameOrNpwpByValue;
        partnerWithNameOrNpwpByValue = partnerRepository.findAllByPartNameContainingOrPartNpwpContaining(value, value, pageable);
        return partnerWithNameOrNpwpByValue;
    }
}
