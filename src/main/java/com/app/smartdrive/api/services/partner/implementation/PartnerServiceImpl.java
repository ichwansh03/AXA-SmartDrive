package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final CityRepository cityRepository;
    @Override
    public Partner getById(Long entityId) {
        return null;
    }

    @Override
    public List<Partner> getAll() {
        return null;
    }

    @Override
    public Partner save(Partner entity) {
        return partnerRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
