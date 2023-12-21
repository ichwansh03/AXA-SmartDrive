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
import java.util.ArrayList;
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


    @Override
    public PartnerContact getById(PartnerContactEntityId partnerContactEntityId) {
        return partnerContactRepository.findById(partnerContactEntityId).orElseThrow(()->new EntityNotFoundException("Partner Contact Not Found"));
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
        if(request.isGrantUserAccess()){
            userDto.setUserPassword(request.getPhone());
        }
        return userService.createUser(userDto);
    }

    private UserPhoneDto createUserPhoneDto(String phoneNumber){
        UserPhoneDto userPhoneDto = new UserPhoneDto();
        userPhoneDto.setUsphPhoneType("HP");
        userPhoneDto.setUserPhoneId(new UserPhoneIdDto(phoneNumber));
        return userPhoneDto;
    }

    private void createUserPhoneDtoList(String phoneNumber, User user){
        List<UserPhoneDto> userPhoneDtoList = new ArrayList<>();
        UserPhoneDto userPhoneDto = new UserPhoneDto();
        UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto(phoneNumber);
        userPhoneDto.setUserPhoneId(userPhoneIdDto);
        userPhoneDtoList.add(userPhoneDto);
        userPhoneService.createUserPhone(user, userPhoneDtoList);
    }
    @Transactional
    public PartnerContact create(PartnerContactRequest request) {
        Partner partner = partnerService.getById(request.getPartnerId());
        User user = createUser(request);

        userRolesService.createUserRole(EnumUsers.RoleName.PR, user, true);
        createUserPhoneDtoList(request.getPhone(), user);
        user = userService.save(user);

        PartnerContact partnerContact = new PartnerContact();
        partnerContact.setUser(user);
        partnerContact.setPartner(partner);
        PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId(partner.getPartEntityid(), user.getUserEntityId());
        partnerContact.setId(partnerContactEntityId);
        partnerContactRepository.save(partnerContact);

        return partnerContact;
    }

    @Transactional
    public PartnerContact edit(PartnerContactRequest request, Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new EntityNotFoundException("Partner Contact Not Found"));
        Partner partner = partnerService.getById(request.getPartnerId());
        UserPhone userPhone = user.getUserPhone().get(0);
         if(!userPhone.getUserPhoneId().getUsphPhoneNumber().equals(request.getPhone())){
            user.getUserPhone().clear();

            createUserPhoneDtoList(request.getPhone(), user);
            user = userService.save(user);

            PartnerContact partnerContact = new PartnerContact();
            partnerContact.setUser(user);
            partnerContact.setPartner(partner);

            PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId(partner.getPartEntityid(), user.getUserEntityId());
            partnerContact.setId(partnerContactEntityId);
            partnerContactRepository.save(partnerContact);
        }
        user.setUserFullName(request.getName());
        userService.save(user);

        PartnerContactEntityId id = new PartnerContactEntityId();
        id.setPartnerId(partner.getPartEntityid());
        id.setUserId(user.getUserEntityId());

        return partnerContactRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Partner Contact Not Found"));
    }
}
