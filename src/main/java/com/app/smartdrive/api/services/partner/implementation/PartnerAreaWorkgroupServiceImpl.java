package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.partner.PartnerAreaWorkGroupRepository;
import com.app.smartdrive.api.repositories.partner.PartnerContactRepository;
import com.app.smartdrive.api.services.partner.PartnerAreaWorkgroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerAreaWorkgroupServiceImpl implements PartnerAreaWorkgroupService {

    private final ArwgRepository arwgRepository;
    private final PartnerContactRepository partnerContactRepository;
    private final PartnerAreaWorkGroupRepository partnerAreaWorkGroupRepository;



    @Override
    public PartnerAreaWorkgroup getById(Long aLong) {
        return null;
    }

    @Override
    public List<PartnerAreaWorkgroup> getAll() {
        return null;
    }

    @Override
    public PartnerAreaWorkgroup save(PartnerAreaWorkgroup entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    @Transactional
    public PartnerAreaWorkgroup save(PartnerAreaWorkgroupRequest request) {
        PartnerAreaWorkgroup pawo = new PartnerAreaWorkgroup();
        AreaWorkGroup areaWorkGroup = arwgRepository.findByArwgCode(request.getWorkgroup());
        PartnerContact partnerContact = partnerContactRepository.findByUser_UserFullName(request.getPartnerContactName()).get();
        pawo.setPartnerContact(partnerContact);
        pawo.setAreaWorkGroup(areaWorkGroup);
        PartnerAreaWorkGroupId id = new PartnerAreaWorkGroupId();
        id.setPartnerId(partnerContact.getId().getPartnerId());
        id.setAreaWorkGroup(areaWorkGroup.getArwgCode());
        id.setUserId(partnerContact.getId().getUserId());
        pawo.setId(id);

        return partnerAreaWorkGroupRepository.save(pawo);
    }
}
