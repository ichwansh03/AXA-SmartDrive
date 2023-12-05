package com.app.smartdrive.api.services.payment.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import com.app.smartdrive.api.mapper.payment.UserAccounts.UserAccountsMapperResponse;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.services.payment.UserAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountsImpl implements UserAccountsService {
    private final UserAccountsRepository repositoryUA;

    @Override
    public Boolean addDebitCredit(Long usac_id,UserAccountsDtoRequests userAccountsDtoRequests) {
        Optional<UserAccounts> idData = repositoryUA.findById(usac_id);
        List<UserAccounts> listData = repositoryUA.findAll();
        UserAccounts userAcc = idData.get();
        if(idData.isPresent()){
            for (UserAccounts user : listData) {
                if(usac_id.equals(user.getUsac_id())){
                    userAcc.setUsac_debet(userAccountsDtoRequests.getUsac_debet());
                    userAcc.setUsac_credit(userAccountsDtoRequests.getUsac_credit());
                    repositoryUA.save(userAcc);
                    return true;
                }
            }
        }
        return false;
    }

    
    @Override
    public Boolean updateDebitCredit(Long usac_id, UserAccountsDtoRequests userAccountsDto) {
        Optional<UserAccounts> idUA = repositoryUA.findById(usac_id);
        List<UserAccounts> listUserAccounts = repositoryUA.findAll();
        UserAccounts userAccData = idUA.get();
        if(idUA.isPresent()){
            for (UserAccounts user : listUserAccounts) {
                if(usac_id.equals(user.getUsac_id())){
                    if(userAccountsDto.getUsac_debet()!=null && userAccountsDto.getUsac_credit()!=null){
                        userAccData.setUsac_debet(userAccountsDto.getUsac_debet());
                        userAccData.setUsac_credit(userAccountsDto.getUsac_credit());
                        repositoryUA.save(userAccData);
                    }else if(userAccountsDto.getUsac_debet()!=null){
                        userAccData.setUsac_debet(userAccountsDto.getUsac_debet());
                        repositoryUA.save(userAccData);
                    }else if(userAccountsDto.getUsac_credit()!=null){
                        userAccData.setUsac_credit(userAccountsDto.getUsac_credit());
                        repositoryUA.save(userAccData);
                    }
                    return true;
                    
                }
            }
        }
        return false;
    }

    @Override
    public Boolean deleteUAById(Long usac_id) {
        int deleteId = repositoryUA.deleteUserAccountById(usac_id);
        if(deleteId != 1){
            return false;
        }
        return true;
    }



    @Override
    public List<UserAccountsDtoResponse> listDtoResponses() {
        List<UserAccounts> listUA = repositoryUA.findAll();
        List<UserAccountsDtoResponse> listDto = new ArrayList<>();
        for (UserAccounts user : listUA) {
            UserAccountsDtoResponse userDto = UserAccountsMapperResponse.convertEnityToDto(user);
            listDto.add(userDto);
        }
        return listDto;
    }

    

    @Override
    public UserAccountsDtoResponse getIdUser(Long usac_id) {
        Optional<UserAccounts> listUA = repositoryUA.findById(usac_id);
        UserAccounts uA = listUA.get();
        UserAccountsDtoResponse userR = new UserAccountsDtoResponse();
        userR.setUsac_accountno(uA.getUsac_accountno());
        userR.setUsac_debet(uA.getUsac_debet());
        userR.setUsac_credit(uA.getUsac_credit());
        userR.setEnumPaymentType(uA.getEnumPaymentType());
        
        return userR;
    }



    @Override
    public List<UserAccounts> getAll() {
   
        return repositoryUA.findAll();
    }

    @Override
    public UserAccounts getById(Long id) {
        return repositoryUA.findById(id).get();
    }

    @Override
    public UserAccounts save(UserAccounts entity) {
        return repositoryUA.save(entity);
    }


    
    
    
    
    
}
