package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
