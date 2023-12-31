package com.app.smartdrive.api.services.payment;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import java.math.BigDecimal;
import java.util.List;

import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
public interface AccountsUserManagementService {

    default boolean checkValidationNoAccount(String noRekFrom, String noRekTo, UserAccountsRepository repository){
        UserAccounts userFrom = repository.findByAccounts(noRekFrom);
        UserAccounts userTo = repository.findByAccounts(noRekTo);

        if(userFrom == null || userTo == null){
            return false;
        }
        return true;

    }


    default void checkErrorAccount(String noRekFrom, String noRekTo, UserAccountsRepository repository){
        UserAccounts userFrom = repository.findByAccounts(noRekFrom);
        UserAccounts userTo = repository.findByAccounts(noRekTo);

        if(userFrom == null){
            throw new EntityNotFoundException("Rekening pengirim: "
                    + noRekFrom + " Belum terdaftar!, Harap memasukan norekening yang sesuai ");
        } else if (userTo == null) {
            throw new EntityNotFoundException("Rekening penerima: "
                    + noRekTo + " Belum terdaftar!, Harap memasukan norekening yang sesuai ");
        }

    }

    default void calculationTransaksiDebetProcess(String noRekFrom, String noRekTo, BigDecimal nominal, UserAccountsRepository repository){

        UserAccounts userRecipient = repository.findByAccounts(noRekTo);
        UserAccounts userSender = repository.findByAccounts(noRekFrom);
        BigDecimal saldoRecipient = userRecipient.getUsacDebet();
        BigDecimal saldoSender = userSender.getUsacDebet();
        if(checkValidationNoAccount(noRekFrom, noRekTo, repository)){
            if(saldoRecipient == null){
                userSender.setUsacDebet(saldoSender.subtract(nominal));
                userRecipient.setUsacDebet(nominal);
            }else{
                BigDecimal totalSaldoRecipient = saldoRecipient.add(nominal);
                BigDecimal totalSaldoSender = saldoSender.subtract(nominal);
                userSender.setUsacDebet(totalSaldoSender);
                userRecipient.setUsacDebet(totalSaldoRecipient);
            }
            repository.save(userSender);
            repository.save(userRecipient);
        }
    }
    default void calculationAddDebetProcess(String noRek, BigDecimal nominal, UserAccountsRepository repository){
        UserAccounts userRecipient = repository.findByAccounts(noRek);
        BigDecimal saldoRecipient = userRecipient.getUsacDebet();
        BigDecimal totalSaldoRecipient = saldoRecipient.add(nominal);
        userRecipient.setUsacDebet(totalSaldoRecipient);
        repository.save(userRecipient);
    }
}
