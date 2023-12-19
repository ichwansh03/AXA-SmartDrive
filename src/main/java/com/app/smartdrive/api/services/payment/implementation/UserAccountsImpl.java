package com.app.smartdrive.api.services.payment.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.NoRekNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequestsFirst;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsListDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.payment.UserAccounts.UserAccountsListAllMapperResponse;
import com.app.smartdrive.api.mapper.payment.UserAccounts.UserAccountsMapperResponse;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.services.payment.UserAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountsImpl implements UserAccountsService {
    private final UserAccountsRepository repositoryUA;

    @Override
    public UserAccountsDtoResponse addDebit(UserAccountsDtoRequests requests) {
        List<UserAccounts> listAcc = repositoryUA.findAll();
        UserAccounts user = new UserAccounts();
        UserAccountsDtoResponse dto = new UserAccountsDtoResponse();
        user.setUsac_debet(requests.getNominall());
        
        UserAccounts userAcc = repositoryUA.findByNorek(requests.getNoRekening()).orElse(null);
        String noAccount = requests.getNoRekening();
        
        dto = UserAccountsMapperResponse.convertEnityToDto(requests);
        if(userAcc!=null){
            for (UserAccounts userAccount : listAcc) {
                if(noAccount.equals(userAccount.getUsac_accountno())){
                    userAccount.setUsac_debet(requests.getNominall());
                    repositoryUA.save(userAccount);
                }
            }
            return dto;
        }else{
            throw new NoRekNotFoundException(noAccount + " Nomor rekening yang anda masukan belum terdaftar");
        }
    
    }

    @Override
    public Boolean deleteUserAccountsByNoRek(UserAccountsDtoRequestsFirst request) {
        int deleteId = repositoryUA.deleteUserAccountByNoRek(request.getNoRekening());
        if(deleteId != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<UserAccountsListDtoResponse> listDtoResponses() {
        List<UserAccounts> listUA = repositoryUA.findAll();
        List<UserAccountsListDtoResponse> listDto = new ArrayList<>();

        if(listUA.isEmpty()){
            throw new EntityNotFoundException("Data masih belum terisi");
        }else{
            for (UserAccounts user : listUA) {
                UserAccountsListDtoResponse userDto = UserAccountsListAllMapperResponse.convertEntityToDto(user);
                listDto.add(userDto);
            }
        }
       
        return listDto;
    }
       
    @Override
    public UserAccountsListDtoResponse getIdUser(Long usac_id) {
        UserAccounts user = repositoryUA.findById(usac_id).orElseThrow(() 
        -> new NoRekNotFoundException("No Rekening belum terdaftar"));
        UserAccountsListDtoResponse dto = new UserAccountsListDtoResponse();
        dto = UserAccountsListAllMapperResponse.convertEntityToDto(user);   
        return dto;
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
