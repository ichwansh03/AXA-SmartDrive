package com.app.smartdrive.api.services.payment.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.services.payment.FintechService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FintechServiceImpl implements FintechService {
    private final FintechRepository fintechRepository;
    private final EntityManager entityManager;
    
    @Transactional
    @Override
    public Fintech addFintech(Fintech fintech) {
        entityManager.persist(fintech);
        return fintech;
    }

    @Override
    public void deleteFintechById(Long fint_entityid) {
        // TODO Auto-generated method stub
        fintechRepository.deleteFintechById(fint_entityid);;
    }

    @Override
    public List<Fintech> findAllFintech() {
        // TODO Auto-generated method stub
        return fintechRepository.findAll();
    }

    @Override
    public Optional<Fintech> getFintechById(Long fint_entityid) {
        // TODO Auto-generated method stub
        return fintechRepository.findById(fint_entityid);
    }

    @Override
    public int updateFintech(String fint_name) {
        // TODO Auto-generated method stub
        return 0;
    };

    

    
}
