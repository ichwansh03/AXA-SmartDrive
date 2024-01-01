package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.services.master.MasterService;

public interface PartnerAreaWorkgroupService extends MasterService<PartnerAreaWorkgroup, PartnerAreaWorkGroupId> {

    PartnerAreaWorkgroup create(PartnerAreaWorkgroupRequest request);

}
