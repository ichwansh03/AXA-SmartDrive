package com.app.smartdrive.api.services.users.implementation;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUserAccountImpl implements UserUserAccountService {
  private final UserAccountsRepository userAccountsRepository;
  private final BanksRepository banksRepository;
  private final FintechRepository fintechRepository;
  private final UserRepository userRepository;

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
        NullUtils.updateIfChanged(userAccounts.get()::setFintech, fintech,
            userAccounts.get()::getFintech);
        NullUtils.updateIfChanged(userAccounts.get()::setUsac_accountno, userPost.getAccNumber(),
            userAccounts.get()::getUsac_accountno);
        return userAccountsRepository.save(userAccounts.get());
      }
    }
    throw new EntityNotFoundException("UserAccount not found or you're not granted access");
  }

  @Override
  public UserAccounts createUserAccounts(Long id, CreateUserDto userPost) {
    UserAccounts userAccounts = new UserAccounts();
    userAccounts.setUsac_accountno(userPost.getAccNumber());
    if (userPost.getAccountType().equals("BANK")) {
      userAccounts.setEnumPaymentType(EnumPaymentType.BANK);
      Banks bank = banksRepository.findByBankNameOptional(userPost.getBank())
          .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
      userAccounts.setBanks(bank);
      userAccounts.setUsacBankEntityid(bank.getBank_entityid());
    }
    if (userPost.getAccountType().equals("FINTECH")) {
      userAccounts.setEnumPaymentType(EnumPaymentType.FINTECH);
      Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getFintech())
          .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
      userAccounts.setFintech(fintech);
    }
    userAccounts.setUsacUserEntityid(id);

    return userAccountsRepository.save(userAccounts);
  }

  @Override
  @Transactional
  public void deleteUserAccounts(Long id, Long ucId) {
    Optional<User> user = userRepository.findById(id);
    Optional<UserAccounts> userAccount = userAccountsRepository.findById(ucId);
    if(user.get().getUserEntityId().equals(userAccount.get().getUsacUserEntityid())){
      userAccountsRepository.delete(userAccount.get());
    }
  }

}
