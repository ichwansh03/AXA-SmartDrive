package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.partner.PartnerAreaWorkGroupRepository;
import com.app.smartdrive.api.repositories.partner.PartnerContactRepository;
import com.app.smartdrive.api.services.master.ArwgService;
import com.app.smartdrive.api.services.partner.PartnerAreaWorkgroupService;
import com.app.smartdrive.api.services.partner.PartnerContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerAreaWorkgroupServiceImpl implements PartnerAreaWorkgroupService {

    private final PartnerAreaWorkGroupRepository partnerAreaWorkGroupRepository;
    private final ArwgService arwgService;
    private final PartnerContactService partnerContactService;


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
    public PartnerAreaWorkgroup create(PartnerAreaWorkgroupRequest request) {
        PartnerAreaWorkgroup pawo = new PartnerAreaWorkgroup();

        AreaWorkGroup areaWorkGroup = arwgService.getById(request.getAreaWorkgroupId());
        PartnerContact partnerContact = partnerContactService.getById(request.getPartnerContactId());

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
