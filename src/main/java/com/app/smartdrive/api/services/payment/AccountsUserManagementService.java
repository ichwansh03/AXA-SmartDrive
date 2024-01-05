package com.app.smartdrive.api.services.payment;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import java.math.BigDecimal;
import java.util.List;

import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
public interface AccountsUserManagementService {

    default boolean checkValidationNoAccount(String noRekFrom, String noRekTo, UserAccountsRepository repository){
        List<UserAccounts> listAcc = repository.findAll();
        for (UserAccounts user: listAcc) {
            if(!noRekFrom.equals(user.getUsac_accountno()) &&
                    !noRekTo.equals(user.getUsac_accountno())){
                return false;
            }
        }
        return true;
    }

    default boolean checkUserAccounts(String noRekening, UserAccountsRepository repository){
        List<UserAccounts> listUserAccounts = repository.findAll();
        for (UserAccounts user: listUserAccounts) {
            if(user.getUsac_accountno().equals(noRekening)){
                return true;
            }
        }
        return false;
    }

    default void checkErrorAccount(String noRekFrom, String noRekTo, UserAccountsRepository repository){
        List<UserAccounts> listAcc = repository.findAll();
        for (UserAccounts user: listAcc) {
            if(!noRekFrom.equals(user.getUsac_accountno())){
                throw new EntityNotFoundException("Rekening pengirim: "
                        + noRekFrom + " Belum terdaftar!, Harap memasukan norekening yang sesuai ");
            } else if (!noRekTo.equals(user.getUsac_accountno())) {
                throw new EntityNotFoundException("Rekening penerima: "
                        + noRekTo + " Belum terdaftar!, Harap memasukan norekening yang sesuai ");
            }
        }
    }

    default void calculationTransaksiDebetProcess(String noRekFrom, String noRekTo, BigDecimal nominal, UserAccountsRepository repository){
        List<UserAccounts> listAcc = repository.findAll();
        UserAccounts userRecipient = repository.findByAccounts(noRekTo);
        UserAccounts userSender = repository.findByAccounts(noRekFrom);
        BigDecimal saldoRecipient = userRecipient.getUsac_debet();
        BigDecimal saldoSender = userSender.getUsac_debet();
        if(checkValidationNoAccount(noRekFrom, noRekTo, repository)){
            BigDecimal totalSaldoRecipient = saldoRecipient.add(nominal);
            BigDecimal totalSaldoSender = saldoSender.subtract(nominal);
            userSender.setUsac_debet(totalSaldoSender);
            userRecipient.setUsac_debet(totalSaldoRecipient);
            repository.save(userSender);
            repository.save(userRecipient);
        }
    }
    default void calculationAddDebetProcess(String noRek, BigDecimal nominal, UserAccountsRepository repository){
        UserAccounts userRecipient = repository.findByAccounts(noRek);
        BigDecimal saldoRecipient = userRecipient.getUsac_debet();
        BigDecimal totalSaldoRecipient = saldoRecipient.add(nominal);
        userRecipient.setUsac_debet(totalSaldoRecipient);
        repository.save(userRecipient);
    }
}
