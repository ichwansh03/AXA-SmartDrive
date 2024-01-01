package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.services.master.MasterService;
import org.springframework.data.domain.Page;

public interface PartnerService extends MasterService<Partner, Long> {
    Partner create(PartnerRequest request);
    Page<Partner> searchByNameOrNpwp(String value, int page);

    Partner save(PartnerRequest request);
}
