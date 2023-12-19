package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserUserAccountImpl implements UserUserAccountService {
  private final UserAccountsRepository userAccountsRepository;
  private final BanksRepository banksRepository;
  private final FintechRepository fintechRepository;
  private final UserRepository userRepository;

  @Override
  public UserAccounts updateUserAccounts(Long id, Long accountId, UserUserAccountDto userPost) {
    // Optional<UserAccounts> userAccounts = userAccountsRepository.findById(accountId);
    // if (userAccounts.isPresent() && userAccounts.get().getUsacUserEntityid().equals(id)) {
    //   if (userAccounts.get().getEnumPaymentType().toString().equals("BANK")) {
    //     Banks bank = banksRepository.findByBankNameOptional(userPost.getBank())
    //         .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
    //     NullUtils.updateIfChanged(userAccounts.get()::setBanks, bank, userAccounts.get()::getBanks);
    //     NullUtils.updateIfChanged(userAccounts.get()::setUsac_accountno, userPost.getUsac_accountno(),
    //         userAccounts.get()::getUsac_accountno);
    //     return userAccountsRepository.save(userAccounts.get());
    //   } else {
    //     Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getFintech())
    //         .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
    //     NullUtils.updateIfChanged(userAccounts.get()::setFintech, fintech,
    //         userAccounts.get()::getFintech);
    //     NullUtils.updateIfChanged(userAccounts.get()::setUsac_accountno, userPost.getUsac_accountno(),
    //         userAccounts.get()::getUsac_accountno);
    //     return userAccountsRepository.save(userAccounts.get());
    //   }
    // }
    throw new EntityNotFoundException("UserAccount not found or you're not granted access");
  }

  @Override
  public UserAccounts addUserAccounts(Long id, UserUserAccountDto userPost) {
    UserAccounts userAccounts = new UserAccounts();
    // userAccounts.setUsac_accountno(userPost.getUsac_accountno());
    // if (userPost.getEnumPaymentType().equals("BANK")) {
    //   userAccounts.setEnumPaymentType(EnumPaymentType.BANK);
    //   Banks bank = banksRepository.findByBankNameOptional(userPost.getBank())
    //       .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
    //   userAccounts.setBanks(bank);
    //   userAccounts.setUsacBankEntityid(bank.getBank_entityid());
    // }
    // if (userPost.getEnumPaymentType().equals("FINTECH")) {
    //   userAccounts.setEnumPaymentType(EnumPaymentType.FINTECH);
    //   Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getFintech())
    //       .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
    //   userAccounts.setFintech(fintech);
    // }
    // userAccounts.setUsacUserEntityid(id);

    return userAccountsRepository.save(userAccounts);
  }

  @Override
  @Transactional
  public void deleteUserAccounts(Long id, Long accountId) {
    Optional<User> user = userRepository.findById(id);
    Optional<UserAccounts> userAccount = userAccountsRepository.findById(accountId);
    if(user.get().getUserEntityId().equals(userAccount.get().getUsacUserEntityid())){
      userAccountsRepository.delete(userAccount.get());
    }
  }

  @Override
  public List<UserAccounts> createUserAccounts(List<UserUserAccountDto> userPost, User user, Long paymentId) {
    List<UserAccounts> userAccounts = TransactionMapper.mapListDtoToListEntity(userPost, UserAccounts.class);
    for (int i = 0; i < userAccounts.size(); i++) {
      if(userAccounts.get(i).getEnumPaymentType().equals(EnumPaymentType.BANK)){
        userAccounts.get(i).setBanks(banksRepository.findById(paymentId).orElse(null));
        userAccounts.get(i).setUsacBankEntityid(paymentId);
      }
      if(userAccounts.get(i).getEnumPaymentType().equals(EnumPaymentType.FINTECH)){
        userAccounts.get(i).setFintech(fintechRepository.findById(paymentId).orElse(null));
        userAccounts.get(i).setUsacFintEntityid(paymentId);
      }
      userAccounts.get(i).setUser(user);
      userAccounts.get(i).setUsacUserEntityid(user.getUserEntityId());
    }
    user.setUserAccounts(userAccounts);
    return userAccounts;
  }
}
