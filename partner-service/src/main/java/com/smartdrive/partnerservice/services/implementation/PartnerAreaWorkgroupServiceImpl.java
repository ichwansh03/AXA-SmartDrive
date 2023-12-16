package com.smartdrive.partnerservice.services.implementation;

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
//    private final ArwgService arwgService;
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

//        AreaWorkGroup areaWorkGroup = arwgService.getById(request.getAreaWorkgroupId());
        PartnerContact partnerContact = partnerContactService.getById(request.getPartnerContactId());

        pawo.setPartnerContact(partnerContact);
//        pawo.setAreaWorkGroup(areaWorkGroup);
        PartnerAreaWorkGroupId id = new PartnerAreaWorkGroupId();
        id.setPartnerId(partnerContact.getId().getPartnerId());
//        id.setAreaWorkGroup(areaWorkGroup.getArwgCode());
        id.setUserId(partnerContact.getId().getUserId());
        pawo.setId(id);

        return partnerAreaWorkGroupRepository.save(pawo);
    }

}
