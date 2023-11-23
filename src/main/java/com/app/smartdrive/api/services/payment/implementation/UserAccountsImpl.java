package com.app.smartdrive.api.services.payment.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.services.payment.UserAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountsImpl implements UserAccountsService {
    private final UserAccountsRepository repository;

    @Override
    public List<UserAccounts> findAllUserAccounts() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public UserAccounts addUserAcc(UserAccounts userAccounts) {
        // TODO Auto-generated method stub
        return repository.save(userAccounts);
    }

    @Override
    public void deleteUserAccountsById(Long usac_id) {
        // TODO Auto-generated method stub
        repository.deleteById(usac_id);
        
    }

    @Override
    public Optional<UserAccounts> findByIdUserAcc(Long usac_id) {
        // TODO Auto-generated method stub
        return repository.findById(usac_id);
    }

    @Override
    public UserAccounts updateUserAcc(UserAccounts userAccounts) {
        // TODO Auto-generated method stub
        return repository.save(userAccounts);
    }

    
    
    
}
