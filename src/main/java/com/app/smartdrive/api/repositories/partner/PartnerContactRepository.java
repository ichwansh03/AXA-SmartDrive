package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerContactRepository extends JpaRepository<PartnerContact, PartnerContactEntityId> {

    Optional<PartnerContact> findByUser_UserFullName(String name);
}
