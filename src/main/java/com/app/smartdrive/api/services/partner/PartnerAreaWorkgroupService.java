package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.services.master.MasterService;

import java.util.List;

public interface PartnerAreaWorkgroupService {

    PartnerAreaWorkgroup create(PartnerAreaWorkgroupRequest request);
    PartnerAreaWorkgroup getById(PartnerAreaWorkGroupId id);

    List<PartnerAreaWorkgroup> getAll();
    void deleteById(PartnerAreaWorkGroupId id);

}
