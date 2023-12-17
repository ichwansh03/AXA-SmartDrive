package com.smartdrive.partnerservice.services.implementation;

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
import org.springframework.web.client.RestTemplate;

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
        RestTemplate restTemplate = new RestTemplate();
        PartnerAreaWorkgroup pawo = new PartnerAreaWorkgroup();
        String areaWorkGroupUrl = "http://localhost:8222/master/arwg/" + request.getAreaWorkgroupId();
        ArwgRes arwgRes = restTemplate.getForObject(areaWorkGroupUrl, ArwgRes.class);

        PartnerContact partnerContact = partnerContactService.getById(request.getPartnerContactId());

        pawo.setPartnerContact(partnerContact);
        PartnerAreaWorkGroupId id = new PartnerAreaWorkGroupId();
        id.setPartnerId(partnerContact.getId().getPartnerId());
        id.setAreaWorkGroup(arwgRes.getArwgCode());
        id.setUserId(partnerContact.getId().getUserId());
        pawo.setId(id);

        return partnerAreaWorkGroupRepository.save(pawo);
    }

}
