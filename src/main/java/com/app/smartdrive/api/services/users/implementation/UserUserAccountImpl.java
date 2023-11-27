package com.app.smartdrive.api.services.users.implementation;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUserAccountImpl implements UserUserAccountService {
  private final UserAccountsRepository userAccountsRepository;
  private final BanksRepository banksRepository;
  private final FintechRepository fintechRepository;

  @Override
  public UserAccounts updateUserAccounts(Long id, Long accountId, CreateUserDto userPost) {
    Optional<UserAccounts> userAccounts = userAccountsRepository.findById(accountId);
    if (userAccounts.isPresent() && userAccounts.get().getUsacUserEntityid().equals(id)) {
      if (userAccounts.get().getEnumPaymentType().toString().equals("BANK")) {
        Banks bank = banksRepository.findByBankNameOptional(userPost.getBank())
            .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
        NullUtils.updateIfChanged(userAccounts.get()::setBanks, bank, userAccounts.get()::getBanks);
        NullUtils.updateIfChanged(userAccounts.get()::setUsac_accountno, userPost.getAccNumber(),
            userAccounts.get()::getUsac_accountno);
        return userAccountsRepository.save(userAccounts.get());
      } else {
        Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getFintech())
            .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
        NullUtils.updateIfChanged(userAccounts.get()::setFintech, fintech, userAccounts.get()::getFintech);
        NullUtils.updateIfChanged(userAccounts.get()::setUsac_accountno, userPost.getAccNumber(),
            userAccounts.get()::getUsac_accountno);
        return userAccountsRepository.save(userAccounts.get());
      }
    }
    throw new EntityNotFoundException("UserAccount not found or you're not granted access");
  }

}
