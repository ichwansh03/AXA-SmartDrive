package com.app.smartdrive.api.services.users.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.user.UserMapper;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final EntityManager entityManager;
  private final UserRepository userRepo;
  @Override
  public UserDto getById(Long id) {
    // TODO Auto-generated method stub
    return UserMapper.convertUserToDto(userRepo.findById(id).get());
  }

  @Override
  public List<UserDto> getAll() {
    // TODO Auto-generated method stub
    List<User> users = userRepo.findAll();
    List<UserDto> userDto = new ArrayList<>();
    for (User user : users) {
      userDto.add(UserMapper.convertUserToDto(user));
    }
    return userDto;
  }

  @Override
  public UserDto save(UserDto entity) {
    // TODO Auto-generated method stub

    return null;
  }

  @Override
  public void deleteById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

  @Transactional
  @Override
  public User save(User user) {
    entityManager.persist(user);
    return user;
  }
  
}
