package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.services.master.MasterService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PartnerService {
    Partner create(PartnerRequest request);
    Page<Partner> searchByNameOrNpwp(String value, int page);
    Partner save(PartnerRequest request);
    Partner getById(Long entityId);
    Partner save(Partner partner);
    List<Partner> getAll();
}
