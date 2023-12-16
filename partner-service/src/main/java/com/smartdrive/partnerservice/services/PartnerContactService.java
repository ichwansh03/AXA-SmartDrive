package com.smartdrive.partnerservice.services;


import com.smartdrive.partnerservice.dto.request.PartnerContactRequest;
import com.smartdrive.partnerservice.entities.PartnerContact;
import com.smartdrive.partnerservice.entities.PartnerContactEntityId;

public interface PartnerContactService extends BaseService<PartnerContact, PartnerContactEntityId> {
    PartnerContact create(PartnerContactRequest request);
    PartnerContact edit(PartnerContactRequest request, Long userId);
}