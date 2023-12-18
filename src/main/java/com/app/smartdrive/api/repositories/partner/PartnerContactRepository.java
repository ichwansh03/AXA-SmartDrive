package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerContactRepository extends JpaRepository<PartnerContact, PartnerContactEntityId> {
    List<PartnerContact> findByPartner(Partner partner);
}
