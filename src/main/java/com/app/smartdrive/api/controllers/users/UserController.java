package com.app.smartdrive.api.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.LoginDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody LoginDto login){
    return ResponseEntity.status(HttpStatus.OK).body(userService.loginCu(login.getIden(), login.getPassword()));
  }

  @PostMapping("/emps/signin")
  public ResponseEntity<?> loginEm(@RequestBody LoginDto login){
    return ResponseEntity.status(HttpStatus.OK).body(userService.loginEm(login.getIden(), login.getPassword()));
  }

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllDto();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") Long id) {
    return userService.getByIdDto(id);
  }

  @PostMapping
  public ResponseEntity<?> addUser(@ModelAttribute CreateUserDto userPost) throws Exception{
    User userSaved = userService.create(userPost);
    UserDto userDto = TransactionMapper.mapEntityToDto(userSaved, UserDto.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto); //pake dto
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateUser(@ModelAttribute CreateUserDto userPost,
      @PathVariable("id") Long id) {

    User userUpdated = userService.save(userPost, id);
    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
    Optional<User> user = userService.getUserById(id);
    if(user.isPresent()){
      userService.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body("User sudah dihapus");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
    }
}
