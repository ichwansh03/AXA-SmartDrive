package com.app.smartdrive.api.repositories.partner;

import com.app.smartdrive.api.entities.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Page<Partner> findAllByPartNameContainingOrPartNpwpContaining(String name, String npwp, Pageable pageable);

}
