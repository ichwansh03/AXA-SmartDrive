package com.app.smartdrive.api.services.payment.implementation;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.NoRekAccDtoRequest;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.mapper.TransactionMapper;
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
    public UserAccountsDtoResponse getById(Long id) {
        Optional<UserAccounts> findNorekById = repositoryUA.findById(id);
        UserAccounts user = findNorekById.get();
        UserAccounts userAcc = UserAccounts.builder()
                .usac_accountno(user.getUsac_accountno())
                .usac_debet(user.getUsac_debet())
                .enumPaymentType(user.getEnumPaymentType())
                .build();
        UserAccountsDtoResponse response = TransactionMapper.mapEntityToDto(userAcc,UserAccountsDtoResponse.class);
        return response;
    }

    @Override
    public Boolean deleteUserAccountByNorek(NoRekAccDtoRequest request) {
        int deleteId = repositoryUA.deleteUserAccountByNoRek(request.getNoRekening());
        return deleteId == 1;
    }

    @Override
    public List<UserAccountsDtoResponse> getAll() {
        List<UserAccounts> listUser = repositoryUA.findAll();
        List<UserAccountsDtoResponse> listDto = TransactionMapper.mapEntityListToDtoList(listUser, UserAccountsDtoResponse.class);
        return listDto;
    }

    @Override
    public Boolean deleteById(Long id) {
        UserAccounts findUserAccNoRek = repositoryUA.findById(id).orElse(null);
        if(findUserAccNoRek == null){
            throw new EntityNotFoundException(id + " Tidak terdaftar");
        }else{
            repositoryUA.deleteById(id);
        }
        return true;
    }

    private boolean checkUserAccounts(String noRekening){
        List<UserAccounts> listUserAccounts = repositoryUA.findAll();
        for (UserAccounts user: listUserAccounts) {
            if(user.getUsac_accountno().equals(noRekening)){
                return true;
            }
        }
        return false;
    }



    @Override
    public UserAccountsDtoResponse checkSaldo(NoRekAccDtoRequest requests) {
        Optional<UserAccounts> userRekening = repositoryUA.findByNorek(requests.getNoRekening());
        UserAccounts user = userRekening.get();
        UserAccounts userAcc = UserAccounts.builder()
                .usac_accountno(user.getUsac_accountno())
                .usac_debet(user.getUsac_debet())
                .enumPaymentType(user.getEnumPaymentType())
                .build();
        UserAccountsDtoResponse response = TransactionMapper.mapEntityToDto(userAcc,UserAccountsDtoResponse.class);
        return response;
    }

    @Override
    public UserAccountsDtoResponse addDebet(UserAccountsDtoRequests requests) {
        UserAccounts user = new UserAccounts();
        UserAccounts userAcc = repositoryUA.findByNorek(requests.getNoRekening()).orElse(null);
        String noAccount = requests.getNoRekening();

        if(checkUserAccounts(requests.getNoRekening())){
            user.setEnumPaymentType(userAcc.getEnumPaymentType());
            user.setUsac_debet(requests.getNominall());
            userAcc.setUsac_debet(requests.getNominall());
            repositoryUA.save(userAcc);
            return UserAccountsMapperResponse
                    .convertEnityToDto(user,requests);
        }else{
            throw new NoRekNotFoundException(noAccount + " Nomor rekening yang anda masukan belum terdaftar");
        }
    }
}
