package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerAreaWorkGroupRepository extends JpaRepository<PartnerAreaWorkgroup, PartnerAreaWorkGroupId> {
}
