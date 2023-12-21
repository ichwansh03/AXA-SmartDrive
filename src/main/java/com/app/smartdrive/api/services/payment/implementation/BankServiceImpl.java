package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.payment.Banks.BanksMapper;
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
    public BanksDtoResponse addBankss(BanksDtoRequests requests) {
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        BusinessEntity businessEntityId = businessEntityService.save(busines);

        Banks banks = new Banks();
        banks.setBusinessEntity(busines);
        banks.setBank_entityid(businessEntityId.getEntityId());
        banks.setBank_name(requests.getBank_name());
        banks.setBank_desc(requests.getBank_desc());
        
        BanksDtoResponse dto = BanksMapper.convertEntityToDto(banks);

        String bankName = requests.getBank_name();
        Banks banksName = banksRepository.findByBankNameOptional(bankName).orElse(null);

        if(banksName == null){
            addaBanks(banks);
            return dto;
        }else{
            throw new EntityNotFoundException(bankName + " Sudah Terdaftar");
        }
    }

    @Override
    public BanksDtoResponse getById(Long id) {
        Banks banks = banksRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException(id + " Tidak terdaftar "));
        BanksDtoResponse dto = BanksMapper.convertEntityToDto(banks);
        return dto;
    }

    @Override
    public BanksDtoResponse getBankById(Long id) {
        Banks banks = banksRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException(id + " Tidak terdaftar "));
        BanksDtoResponse dto = BanksMapper.convertEntityToDto(banks);
        return dto;
    }

    @Override
    public List<BanksDtoResponse> getAll() {
        List<Banks> banksList = banksRepository.findAll();
        List<BanksDtoResponse> banksDtos = new ArrayList<>();
        if(banksList.isEmpty()){
            throw new EntityNotFoundException("Data masih kosong");
        }else{
            for (Banks banks : banksList) {
                BanksDtoResponse dto = BanksMapper.convertEntityToDto(banks);
                banksDtos.add(dto);
            }
        }
        return banksDtos;
    }

    @Override
    public Boolean updateBanks(Long bank_entityid,BanksDtoResponse banksDto) {
        Banks banks = banksRepository.findById(bank_entityid).orElse(null);
        List<BusinessEntity> listBusinessEntities = businessEntityService.getAll();
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        if(banks == null){
            throw new EntityNotFoundException("Terjadi kesalahan dalam mengupdate banks");
        }else{
            for (BusinessEntity bisnis: listBusinessEntities) {
                if(bank_entityid.equals(bisnis.getEntityId())){
                    if(banksDto.getBank_name()!=null && banksDto.getBank_desc()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        banks.setBank_name(banksDto.getBank_name());
                        banks.setBank_desc(banksDto.getBank_desc());
                        repositoryBisnis.save(bisnis);
                        banksRepository.save(banks);
                    }
                   
                }
            }
            return true;
        }

    }

    @Override
    public Boolean deleteBanks(Long bank_entityid) {
        Banks idBanks = banksRepository.findById(bank_entityid).orElse(null);
        List<BusinessEntity> listBisnis = repositoryBisnis.findAll();
        if(idBanks == null){
            throw new EntityNotFoundException("ID : " + bank_entityid + " Tidak terdaftar ");
        }else{
            for (BusinessEntity bisnis : listBisnis) {
                if(bank_entityid.equals(bisnis.getEntityId())){
                    banksRepository.deleteByID(bank_entityid);
                    repositoryBisnis.deleteById(bisnis.getEntityId());
                }
            }
            return true;
        }
    }

    @Override
    public BanksIdForUserDtoResponse getBanksUser(String bank_name) {
        List<Banks> banksData = banksRepository.findAll();
        Banks banksName = banksRepository.findByBankNameOptional(bank_name).orElse(null);
        BanksIdForUserDtoResponse bankDtoId = new BanksIdForUserDtoResponse();
        
        if(banksName == null){
            throw new UserNotFoundException(bank_name + " Tidak terdaftar");
        }else{
            for (Banks banks : banksData) {
                if(bank_name.equals(banks.getBank_name())){
                    bankDtoId.setBank_entity_id(banks.getBank_entityid());
                }
            }
        }
        return bankDtoId;
    }

    @Override
    public BanksDtoResponse save(BanksDtoResponse entity) {
        return null;
    }

}
