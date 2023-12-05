package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneIdDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.repositories.partner.PartnerContactRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import com.app.smartdrive.api.services.partner.PartnerContactService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerContactServiceImpl implements PartnerContactService {

    private final PartnerContactRepository partnerContactRepository;
    private final PartnerService partnerService;
    private final UserService userService;
    private final UserRolesService userRolesService;
    private final UserPhoneService userPhoneService;
    private final UserRepository userRepository;
    private final UserPhoneRepository userPhoneRepository;

    @Override
    public PartnerContact getById(PartnerContactEntityId partnerContactEntityId) {
        return partnerContactRepository.findById(partnerContactEntityId).get();
    }

    @Override
    public List<PartnerContact> getAll() {
        return partnerContactRepository.findAll();
    }

    @Override
    @Transactional
    public PartnerContact save(PartnerContact entity) {
        return partnerContactRepository.save(entity);
    }

    @Override
    public void deleteById(PartnerContactEntityId partnerContactEntityId) {
        partnerContactRepository.deleteById(partnerContactEntityId);

    }

    @Transactional
    public User createUser(PartnerContactRequest request){
        ProfileRequestDto userDto = new ProfileRequestDto();
        String username = request.getName().replaceAll("\\s", "");
        username = String.format("%15s",username).replace(" ","0");
        userDto.setUserName(username);
        userDto.setUserEmail(username.concat("@gmail.com"));
        userDto.setUserFullName(request.getName());
        userDto.setUserNpwp(request.getPhone());
        userDto.setUserNationalId(request.getPhone());

        User user = userService.createUser(userDto);

        userRolesService.createUserRole(EnumUsers.RoleName.PR, user);
        userService.save(user);
        log.info("user id" + user.getUserEntityId());
        return user;
    }

    private UserPhoneDto createUserPhoneDto(String phoneNumber){
        UserPhoneDto userPhoneDto = new UserPhoneDto();
        userPhoneDto.setUsphPhoneType("HP");
        userPhoneDto.setUserPhoneId(new UserPhoneIdDto(phoneNumber));
        return userPhoneDto;
    }
    @Transactional
    public PartnerContact create(PartnerContactRequest request) {
        User user = userService.getUserById(request.getPartnerId()).orElseThrow(()->new EntityNotFoundException("User not found by id "+request.getPartnerId()));
        Partner partner = partnerService.getById(request.getPartnerId());

        log.info("CheckUserAccess");
        if(request.isGrantUserAccess()){
            user = createUser(request);
        }
        log.info("user id after create {}", user.getUserEntityId());

        PartnerContact partnerContact = new PartnerContact();
        partnerContact.setUser(user);
        partnerContact.setPartner(partner);
        UserPhone userPhone = new UserPhone();
        userPhone.setUser(user);
        userPhone.setUsphModifiedDate(LocalDateTime.now());
        userPhone.setUsphPhoneType("HP");
        UserPhoneId userPhoneId = new UserPhoneId();
        userPhoneId.setUsphEntityId(user.getUserEntityId());
        userPhoneId.setUsphPhoneNumber("123456");
        userPhone.setUserPhoneId(userPhoneId);

        userPhoneRepository.save(userPhone);

        //UserPhone userPhone = userPhoneService.addUserPhone(user.getUserEntityId(), createUserPhoneDto(request.getPhone()));
        log.info("Userphone {}", userPhone.getUserPhoneId().getUsphPhoneNumber());

        PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId(partner.getPartEntityid(), user.getUserEntityId());
        partnerContact.setId(partnerContactEntityId);
        partnerContactRepository.save(partnerContact);
        log.info("partner COntact id {}", partnerContact.getId());
        return partnerContact;

    }

    @Transactional
    public PartnerContact edit(PartnerContactRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        if(Objects.nonNull(request.getPhone())){
            user.getUserPhone().clear();
            UserPhone userPhone = new UserPhone();
            UserPhoneId userPhoneId = new UserPhoneId();
            userPhoneId.setUsphEntityId(user.getUserEntityId());
            userPhoneId.setUsphPhoneNumber(request.getPhone());
            userPhone.setUserPhoneId(userPhoneId);
            user.getUserPhone().add(userPhone);
        }
        user.setUserFullName(request.getName());
        userRepository.save(user);

        PartnerContactEntityId id = new PartnerContactEntityId(request.getPartnerId(), userId);

        return partnerContactRepository.findById(id).get();
    }
}
