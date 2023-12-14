package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemiRepository extends JpaRepository<TemplateInsurancePremi, Long> {
    Optional<TemplateInsurancePremi> findByTemiZonesIdAndTemiIntyNameAndTemiCateId(Long zonesId, String temiIntyName, Long temiCateId);
}
