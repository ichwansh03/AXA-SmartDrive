package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.services.BaseService;

public interface PartnerContactService extends BaseService<PartnerContact, PartnerContactEntityId> {
    PartnerContact create(PartnerContactRequest request);
    PartnerContact edit(PartnerContactRequest request, Long userId);
}