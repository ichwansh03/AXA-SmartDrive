//package com.smartdrive.userservice.services.implementation;
//
//import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
//import com.app.smartdrive.api.dto.user.UserUserAccountDto;
//import com.app.smartdrive.api.dto.user.request.UserUserAccountDtoRequest;
//import com.app.smartdrive.api.entities.payment.Banks;
//import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
//import com.app.smartdrive.api.entities.payment.Fintech;
//import com.app.smartdrive.api.entities.payment.UserAccounts;
//import com.app.smartdrive.api.entities.users.User;
//import com.app.smartdrive.api.mapper.TransactionMapper;
//import com.app.smartdrive.api.repositories.payment.BanksRepository;
//import com.app.smartdrive.api.repositories.payment.FintechRepository;
//import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
//import com.app.smartdrive.api.repositories.users.UserRepository;
//import com.app.smartdrive.api.services.users.UserUserAccountService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserUserAccountImpl implements UserUserAccountService {
//  private final UserAccountsRepository userAccountsRepository;
//  private final BanksRepository banksRepository;
//  private final FintechRepository fintechRepository;
//  private final UserRepository userRepository;
//
//  @Override
//  public UserAccounts updateUserAccounts(Long id, Long accountId, UserUserAccountDtoRequest userPost) {
//     UserAccounts userAccounts = userAccountsRepository.findById(accountId)
//             .orElseThrow(() -> new EntityNotFoundException("UserAccount not found"));
//    userAccounts.setUsac_accountno(userPost.getUsac_accountno());
//    if (userAccounts.getEnumPaymentType() == EnumPaymentType.BANK) {
//      Banks bank = banksRepository.findById(userPost.getPaymentId())
//              .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
//      userAccounts.setBanks(bank);
//    } else if(userAccounts.getEnumPaymentType() == EnumPaymentType.FINTECH){
//      Fintech fintech = fintechRepository.findById(userPost.getPaymentId())
//              .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
//      userAccounts.setFintech(fintech);
//    }
//    return userAccountsRepository.save(userAccounts);
//  }
//
//
//  @Override
//  public UserAccounts addUserAccounts(Long id, UserUserAccountDtoRequest userPost) {
//    UserAccounts userAccounts = new UserAccounts();
//     userAccounts.setUsac_accountno(userPost.getUsac_accountno());
//     if (userPost.getEnumPaymentType() == EnumPaymentType.BANK) {
//       userAccounts.setEnumPaymentType(EnumPaymentType.BANK);
//       Banks bank = banksRepository.findById(userPost.getPaymentId())
//           .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
//       userAccounts.setBanks(bank);
//       userAccounts.setUsacBankEntityid(bank.getBank_entityid());
//     }
//     else if (userPost.getEnumPaymentType() == EnumPaymentType.FINTECH) {
//       userAccounts.setEnumPaymentType(EnumPaymentType.FINTECH);
//       Fintech fintech = fintechRepository.findById(userPost.getPaymentId())
//           .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
//       userAccounts.setFintech(fintech);
//       userAccounts.setUsacFintEntityid(fintech.getFint_entityid());
//     } else {
//       throw new EntityNotFoundException("Wrong Payment type");
//     }
//     userAccounts.setUsacUserEntityid(id);
//     return userAccountsRepository.save(userAccounts);
//  }
//
//  @Override
//  @Transactional
//  public void deleteUserAccounts(Long id, Long accountId) {
//    Optional<User> user = userRepository.findById(id);
//    Optional<UserAccounts> userAccount = userAccountsRepository.findById(accountId);
//    if(user.get().getUserEntityId().equals(userAccount.get().getUsacUserEntityid())){
//      userAccountsRepository.delete(userAccount.get());
//    }
//  }
//
//  @Override
//  public List<UserAccounts> createUserAccounts(List<UserUserAccountDto> userPost, User user, Long paymentId) {
//    List<UserAccounts> userAccounts = TransactionMapper.mapListDtoToListEntity(userPost, UserAccounts.class);
//    for (UserAccounts userAccount : userAccounts) {
//      if (userAccount.getEnumPaymentType().equals(EnumPaymentType.BANK)) {
//        userAccount.setBanks(banksRepository.findById(paymentId).orElse(null));
//        userAccount.setUsacBankEntityid(paymentId);
//      }
//      if (userAccount.getEnumPaymentType().equals(EnumPaymentType.FINTECH)) {
//        userAccount.setFintech(fintechRepository.findById(paymentId).orElse(null));
//        userAccount.setUsacFintEntityid(paymentId);
//      }
//      userAccount.setUser(user);
//      userAccount.setUsacUserEntityid(user.getUserEntityId());
//    }
//    user.setUserAccounts(userAccounts);
//    return userAccounts;
//  }
//}
