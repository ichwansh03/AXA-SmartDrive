package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.entities.payment.Fintech;

public interface FintechService {

    List<Fintech> findAllFintech();
    Fintech addFintech(Fintech fintech);
    int updateFintech(String fint_name);
    Optional<Fintech> getFintechById(Long fint_entityid);
    void deleteFintechById(Long fint_entityid);
    
} 
