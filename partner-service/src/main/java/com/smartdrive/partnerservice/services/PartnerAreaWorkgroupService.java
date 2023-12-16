package com.smartdrive.partnerservice.services;

import com.smartdrive.partnerservice.dto.request.PartnerAreaWorkgroupRequest;
import com.smartdrive.partnerservice.entities.PartnerAreaWorkgroup;

public interface PartnerAreaWorkgroupService extends BaseService<PartnerAreaWorkgroup, Long> {

    PartnerAreaWorkgroup create(PartnerAreaWorkgroupRequest request);

}
