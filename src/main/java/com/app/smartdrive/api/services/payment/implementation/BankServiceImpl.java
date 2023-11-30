package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.BanksDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.payment.BanksMapper;
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
   
    @Transactional
    @Override
    public BanksDto addBanks(BanksDto banksDto) {
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        Long businessEntityId = businessEntityService.save(busines);

        Banks banks = new Banks();
        banks.setBusinessEntity(busines);
        banks.setBank_entityid(businessEntityId);
        banks.setBank_name(banksDto.getBank_name());
        banks.setBank_desc(banksDto.getBank_desc());
        addaBanks(banks);
        BanksDto dto = BanksMapper.convertEntityToDto(banks);

        return dto;
    }

    

    @Override
    public List<BanksDto> getAAll() {
        List<Banks> listBanks = banksRepository.findAll();
        List<BanksDto> listDto = new ArrayList<>();
        for (Banks banks : listBanks) {
            BanksDto banksDto = BanksMapper.convertEntityToDto(banks);
            listDto.add(banksDto);
        }
        return listDto;
    
    }

    @Override
    public List<BanksDto> findById(Long id) {
        Optional<Banks> Id = banksRepository.findById(id);
        List<Banks> listBanks = banksRepository.findAll();
        List<BanksDto> listDto = new ArrayList<>();
        if(Id.isPresent()){
            for (Banks banks : listBanks) {
                if(id.equals(banks.getBank_entityid())){
                    BanksDto dto = BanksMapper.convertEntityToDto(banks);
                    listDto.add(dto);
                }
            }
           
        } return listDto;
    }

    @Transactional
    @Override
    public List<BanksDto> updateBanks(Long bank_entityid,BanksDto banksDto) {
        Optional<Banks> banksId = banksRepository.findById(bank_entityid);
        List<BanksDto> listDto = new ArrayList<>();
        List<BusinessEntity> bE = businessEntityService.getAll();
        Banks bankss = banksId.get();
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        if(banksId.isPresent()){ 
            for (BusinessEntity bisnis: bE) {
                if(bank_entityid.equals(bisnis.getEntityId())){
                    bisnis.setEntityModifiedDate(LocalDateTime.now());
                    bankss.setBank_name(banksDto.getBank_name());
                    bankss.setBank_desc(banksDto.getBank_desc());
                    repositoryBisnis.save(bisnis);
                    banksRepository.save(bankss);
                }
            }
            BanksDto dto = BanksMapper.convertEntityToDto(bankss);
            listDto.add(dto);
                
        }return listDto;
       
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
    public Optional<Banks> getId(Long id) {
        // TODO Auto-generated method stub
        return banksRepository.findById(id);
    }

    @Override
    public Banks getById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    


    @Override
    public List<Banks> getAll() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public Banks save(Banks entity) {
        // TODO Auto-generated method stub
        return null;
    }




   
    

    
    

    

    
    
    
}
