package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.BanksIdForUserDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.payment.BankService;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final EntityManager entityManager;
    private final BanksRepository banksRepository;
    private final BusinessEntityService businessEntityService;
    private final BusinessEntityRepository repositoryBisnis;

    @Transactional
    public Banks addaBanks(Banks banks){
        entityManager.persist(banks);
        return banks;
    }


    @Override
    public Banks getById(Long id) {
        return banksRepository.findById(id).get();
    }


    @Override
    public List<Banks> getAll() {
        return banksRepository.findAll();
    }
    
    @Transactional
    @Override
    public Banks addedBanks(String bank_name, String bank_desc) {
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        Long businessEntityId = businessEntityService.save(busines);
        
        Banks banks = new Banks();
        banks.setBusinessEntity(busines);
        banks.setBank_entityid(businessEntityId);
        banks.setBank_name(bank_name);
        banks.setBank_desc(bank_desc);
        addaBanks(banks);
        return banks;
    }


    @Transactional
    @Override
    public Banks save(Banks entity) {
        return banksRepository.save(entity);
    }


    @Transactional
    @Override
    public Boolean updateBanks(Long bank_entityid,String banks_name, String banks_desc) {
        Optional<Banks> banksId = banksRepository.findById(bank_entityid);
        List<BusinessEntity> bE = businessEntityService.getAll();
        Banks bankss = banksId.get();
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        if(banksId.isPresent()){
            for (BusinessEntity bisnis: bE) {
                if(bank_entityid.equals(bisnis.getEntityId())){
                    if(banks_name!=null && banks_desc!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        bankss.setBank_name(banks_name);
                        bankss.setBank_desc(banks_desc);
                        repositoryBisnis.save(bisnis);
                        banksRepository.save(bankss);
                    }else if(banks_name!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        bankss.setBank_name(banks_name);
                        repositoryBisnis.save(bisnis);
                        banksRepository.save(bankss);
                    }else if(banks_desc!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        bankss.setBank_desc(banks_desc);
                        repositoryBisnis.save(bisnis);
                        banksRepository.save(bankss);
                    }else{
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean deleteBanks(Long bank_entityid) {
        Optional<Banks> idBanks = banksRepository.findById(bank_entityid);
        List<BusinessEntity> listBisnis = repositoryBisnis.findAll();
        if(idBanks.isPresent()){
            for (BusinessEntity bisnis : listBisnis) {
                if(bank_entityid.equals(bisnis.getEntityId())){
                    banksRepository.deleteByID(bank_entityid);
                    repositoryBisnis.deleteById(bisnis.getEntityId());
                }
            }
            return true;
        }else{
            return false;
        }

    }


    @Override
    public BanksIdForUserDto getBanksUser(String bank_name) {
        List<Banks> banksData = banksRepository.findAll();
        Optional<Banks> banksName = banksRepository.findByBankNameOptional(bank_name);
        BanksIdForUserDto bankUser = new BanksIdForUserDto();
        if(banksName.isPresent()){
            for (Banks banks : banksData) {
                if(bank_name.equals(banks.getBank_name())){
                    bankUser.setBank_entity_id(banks.getBank_entityid());
                }
            }
        }
        return bankUser;
    }

    

    



   
    

    
    

    

    
    
    
}
