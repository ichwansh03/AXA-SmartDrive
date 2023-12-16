package com.smartdrive.partnerservice.repositories;

import com.smartdrive.partnerservice.entities.PartnerContact;
import com.smartdrive.partnerservice.entities.PartnerContactEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerContactRepository extends JpaRepository<PartnerContact, PartnerContactEntityId> {
    Optional<PartnerContact> findByUser_UserFullName(String name);
}
