package com.smartdrive.partnerservice.repositories;

import com.smartdrive.partnerservice.entities.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Page<Partner> findAllByPartNameContainingOrPartNpwpContaining(String name, String npwp, Pageable pageable);

    @Query(value = "SELECT * FROM partners.partners part JOIN partners.partner_area_workgroup pawo " +
            "ON part.part_entityid = pawo.pawo_patr_entityid WHERE pawo.pawo_arwg_code = ?1", nativeQuery = true)
    List<Partner> findPartnerByArwgCode(String arwgCode);
}
