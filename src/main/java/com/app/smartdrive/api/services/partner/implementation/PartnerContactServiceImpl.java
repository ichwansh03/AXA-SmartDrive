package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
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
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartnerContactServiceImpl implements PartnerContactService {

    private final PartnerContactRepository partnerContactRepository;
    private final PartnerServiceImpl partnerService;
    private final UserServiceImpl userService;
    private final RolesRepository rolesRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserPhoneRepository userPhoneRepository;
    private final UserRepository userRepository;

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
    public PartnerContact createEntity(PartnerContactRequest request) {

        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        User user = new User();
        user.setUserBusinessEntity(businessEntity);
        user.setUserName(request.getName());
        user.setUserFullName(request.getName()+"-Full");
        user.setUserEmail(request.getName()+"@gmail.com");
        user.setUserNationalId(request.getPhone()+"IDN");
        user.setUserNPWP(request.getName()+"NPWP");
        userService.save(user);

        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRoles(rolesRepository.findById(EnumUsers.RoleName.PR).get());

        UserRolesId userRolesId = new UserRolesId(user.getUserEntityId(), EnumUsers.RoleName.PR);
        userRoles.setUserRolesId(userRolesId);

        userRoleRepository.save(userRoles);

        UserPhone userPhone = new UserPhone();
        userPhone.setUser(user);

        UserPhoneId userPhoneId = new UserPhoneId();
        userPhoneId.setUsphEntityId(user.getUserEntityId());
        userPhoneId.setUsphPhoneNumber(request.getPhone());
        userPhone.setUserPhoneId(userPhoneId);
        userPhoneRepository.save(userPhone);

        Partner partner = partnerService.getById(request.getPartnerId());
        PartnerContact partnerContact = new PartnerContact();
        partnerContact.setPartner(partner);
        partnerContact.setUser(user);

        PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId(partner.getPartEntityid(), user.getUserEntityId());
        partnerContact.setId(partnerContactEntityId);

        return partnerContactRepository.save(partnerContact);

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
