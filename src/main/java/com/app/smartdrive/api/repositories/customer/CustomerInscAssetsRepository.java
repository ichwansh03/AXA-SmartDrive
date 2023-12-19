package com.app.smartdrive.api.repositories.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;

import java.util.Optional;

@Repository
public interface CustomerInscAssetsRepository extends JpaRepository<CustomerInscAssets, Long>{
    Optional<CustomerInscAssets> findByCiasPoliceNumber(String ciasPoliceNumber);
}
