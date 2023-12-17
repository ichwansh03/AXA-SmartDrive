package com.smartdrive.partnerservice.services.implementation;

import com.smartdrive.partnerservice.clients.MasterClient;
import com.smartdrive.partnerservice.dto.ArwgRes;
import com.smartdrive.partnerservice.dto.request.PartnerAreaWorkgroupRequest;
import com.smartdrive.partnerservice.entities.PartnerAreaWorkGroupId;
import com.smartdrive.partnerservice.entities.PartnerAreaWorkgroup;
import com.smartdrive.partnerservice.entities.PartnerContact;
import com.smartdrive.partnerservice.repositories.PartnerAreaWorkGroupRepository;
import com.smartdrive.partnerservice.services.PartnerAreaWorkgroupService;
import com.smartdrive.partnerservice.services.PartnerContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerAreaWorkgroupServiceImpl implements PartnerAreaWorkgroupService {
    private final PartnerAreaWorkGroupRepository partnerAreaWorkGroupRepository;
    private final PartnerContactService partnerContactService;
    private final MasterClient masterClient;


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
    @Transactional
    public PartnerAreaWorkgroup create(PartnerAreaWorkgroupRequest request) {
        PartnerAreaWorkgroup pawo = new PartnerAreaWorkgroup();
        ArwgRes arwgRes = masterClient.findArwgByCode(request.getAreaWorkgroupId());

        PartnerContact partnerContact = partnerContactService.getById(request.getPartnerContactId());

        pawo.setPartnerContact(partnerContact);
        PartnerAreaWorkGroupId id = new PartnerAreaWorkGroupId();
        id.setPartnerId(partnerContact.getId().getPartnerId());
        if (arwgRes != null) {
            id.setAreaWorkGroup(arwgRes.getArwgCode());
        }
        id.setUserId(partnerContact.getId().getUserId());
        pawo.setId(id);

        return partnerAreaWorkGroupRepository.save(pawo);
    }

}
