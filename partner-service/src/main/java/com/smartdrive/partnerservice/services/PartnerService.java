package com.smartdrive.partnerservice.services;

import com.smartdrive.partnerservice.dto.request.PartnerRequest;
import com.smartdrive.partnerservice.entities.Partner;
import org.springframework.data.domain.Page;

public interface PartnerService extends BaseService<Partner, Long> {
    Partner create(PartnerRequest request);
    Page<Partner> searchByNameOrNpwp(String value, int page);

    Partner save(PartnerRequest request);
}
