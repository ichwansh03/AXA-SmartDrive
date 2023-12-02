package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.dto.payment.Response.BanksIdForUserDto;
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
@Transactional
public class BankServiceImpl implements BankService {
    private final EntityManager entityManager;
    private final BanksRepository banksRepository;
    private final BusinessEntityService businessEntityService;
    private final BusinessEntityRepository repositoryBisnis;

    public Banks addaBanks(Banks banks){
        entityManager.persist(banks);
        return banks;
    }


    @Override
    public BanksDto getById(Long id) {
        Optional<Banks> idBanks = banksRepository.findById(id);
        Banks banks = idBanks.get();
        BanksDto dto = BanksMapper.convertEntityToDto(banks);
        return dto;
    }


    @Override
    public List<BanksDto> getAll() {
        List<Banks> banksList = banksRepository.findAll();
        List<BanksDto> banksDtos = new ArrayList<>();
        for (Banks banks : banksList) {
            BanksDto dto = BanksMapper.convertEntityToDto(banks);
            banksDtos.add(dto);
        }
        return banksDtos;
    }

    @Override
    public BanksDto save(BanksDto banksDto) {
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
    public Boolean updateBanks(Long bank_entityid,BanksDto banksDto) {
        Optional<Banks> banksId = banksRepository.findById(bank_entityid);
        List<BusinessEntity> listBusinessEntities = businessEntityService.getAll();
        Banks bankss = banksId.get();
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        if(banksId.isPresent()){
            for (BusinessEntity bisnis: listBusinessEntities) {
                if(bank_entityid.equals(bisnis.getEntityId())){
                    if(banksDto.getBank_name()!=null && banksDto.getBank_desc()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        bankss.setBank_name(banksDto.getBank_name());
                        bankss.setBank_desc(banksDto.getBank_desc());
                        repositoryBisnis.save(bisnis);
                        banksRepository.save(bankss);
                    }else if(banksDto.getBank_name()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        bankss.setBank_name(banksDto.getBank_name());
                        repositoryBisnis.save(bisnis);
                        banksRepository.save(bankss);
                    }else if(banksDto.getBank_desc()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        bankss.setBank_desc(banksDto.getBank_desc());
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
        BanksIdForUserDto bankDtoId = new BanksIdForUserDto();
        if(banksName.isPresent()){
            for (Banks banks : banksData) {
                if(bank_name.equals(banks.getBank_name())){
                    bankDtoId.setBank_entity_id(banks.getBank_entityid());
                }
            }
        }
        return bankDtoId;
    }
}
