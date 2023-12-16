package com.smartdrive.partnerservice.repositories;

import com.smartdrive.partnerservice.entities.PartnerAreaWorkGroupId;
import com.smartdrive.partnerservice.entities.PartnerAreaWorkgroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerAreaWorkGroupRepository extends JpaRepository<PartnerAreaWorkgroup, PartnerAreaWorkGroupId> {

}
