package com.app.smartdrive.api.services.payment.implementation;

import java.util.ArrayList;
import java.util.List;

import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.NoRekNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.mapper.payment.UserAccounts.UserAccountsMapperResponse;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.services.payment.UserAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountsImpl implements UserAccountsService {
    private final UserAccountsRepository repositoryUA;


    @Override
    public UserAccountsMapperResponse getById(Long aLong) {
        return null;
    }

    @Override
    public List<UserAccountsMapperResponse> getAll() {
        return null;
    }

    @Override
    public Boolean deleteById(Long aLong) {
        return null;
    }

    @Override
    public UserAccountsDtoResponse addDebet(UserAccountsDtoRequests requests) {
        List<UserAccounts> listAcc = repositoryUA.findAll();
        UserAccounts user = new UserAccounts();


        user.setUsac_debet(requests.getNominall());

        UserAccounts userAcc = repositoryUA.findByNorek(requests.getNoRekening()).orElse(null);
        String noAccount = requests.getNoRekening();
        UserAccountsDtoResponse dtoResponse = UserAccountsMapperResponse
                .convertEnityToDto(user,requests);
        if(userAcc!=null){
            for (UserAccounts userAccount : listAcc) {
                if(noAccount.equals(userAccount.getUsac_accountno())){
                    userAccount.setUsac_debet(requests.getNominall());
                    repositoryUA.save(userAccount);
                }
            }
            return dtoResponse;
        }else{
            throw new NoRekNotFoundException(noAccount + " Nomor rekening yang anda masukan belum terdaftar");
        }
    }
}
