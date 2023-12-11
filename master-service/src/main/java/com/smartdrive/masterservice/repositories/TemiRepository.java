package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.TemplateInsurancePremi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemiRepository extends JpaRepository<TemplateInsurancePremi, Long> {
    TemplateInsurancePremi findByTemiZonesIdAndTemiIntyNameAndTemiCateId(Long zonesId, String temiIntyName, Long temiCateId);
}
