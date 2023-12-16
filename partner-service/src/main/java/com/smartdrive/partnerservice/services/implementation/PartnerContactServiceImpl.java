package com.smartdrive.partnerservice.services.implementation;

import com.smartdrive.partnerservice.dto.request.PartnerContactRequest;
import com.smartdrive.partnerservice.entities.PartnerContact;
import com.smartdrive.partnerservice.entities.PartnerContactEntityId;
import com.smartdrive.partnerservice.repositories.PartnerContactRepository;
import com.smartdrive.partnerservice.services.PartnerContactService;
import com.smartdrive.partnerservice.services.PartnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerContactServiceImpl implements PartnerContactService {

    private final PartnerContactRepository partnerContactRepository;
    private final PartnerService partnerService;
//    private final UserService userService;
//    private final UserRolesService userRolesService;
//    private final UserPhoneService userPhoneService;


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

    @Override
    public PartnerContact create(PartnerContactRequest request) {
        return null;
    }

    @Override
    public PartnerContact edit(PartnerContactRequest request, Long userId) {
        return null;
    }

    //    @Transactional
//    public User createUser(PartnerContactRequest request){
//        ProfileRequestDto userDto = new ProfileRequestDto();
//        String username = request.getName().replaceAll("\\s", "");
//        username = String.format("%15s",username).replace(" ","0");
//        userDto.setUserName(username);
//        userDto.setUserEmail(username.concat("@gmail.com"));
//        userDto.setUserFullName(request.getName());
//        userDto.setUserNpwp(request.getPhone());
//        userDto.setUserNationalId(request.getPhone());
//        if(request.isGrantUserAccess()){
//            userDto.setUserPassword(request.getPhone());
//        }
//        return userService.createUser(userDto);
//    }

//    private UserPhoneDto createUserPhoneDto(String phoneNumber){
//        UserPhoneDto userPhoneDto = new UserPhoneDto();
//        userPhoneDto.setUsphPhoneType("HP");
//        userPhoneDto.setUserPhoneId(new UserPhoneIdDto(phoneNumber));
//        return userPhoneDto;
//    }
//
//    private void createUserPhoneDtoList(String phoneNumber, User user){
//        List<UserPhoneDto> userPhoneDtoList = new ArrayList<>();
//        UserPhoneDto userPhoneDto = new UserPhoneDto();
//        UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto(phoneNumber);
//        userPhoneDto.setUserPhoneId(userPhoneIdDto);
//        userPhoneDtoList.add(userPhoneDto);
//        userPhoneService.createUserPhone(user, userPhoneDtoList);
//    }
//    @Transactional
//    public PartnerContact create(PartnerContactRequest request) {
//        Partner partner = partnerService.getById(request.getPartnerId());
//        User user = createUser(request);
//
//        userRolesService.createUserRole(EnumUsers.RoleName.PR, user);
//        createUserPhoneDtoList(request.getPhone(), user);
//        user = userService.save(user);
//
//        PartnerContact partnerContact = new PartnerContact();
//        partnerContact.setUser(user);
//        partnerContact.setPartner(partner);
//        PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId(partner.getPartEntityid(), user.getUserEntityId());
//        partnerContact.setId(partnerContactEntityId);
//        partnerContactRepository.save(partnerContact);
//
//        return partnerContact;
//    }

//    @Transactional
//    public PartnerContact edit(PartnerContactRequest request, Long userId) {
//        User user = userService.getUserById(userId).orElseThrow(() -> new EntityNotFoundException("Partner Contact Not Found"));
//        Partner partner = partnerService.getById(request.getPartnerId());
//        UserPhone userPhone = user.getUserPhone().get(0);
//         if(!userPhone.getUserPhoneId().getUsphPhoneNumber().equals(request.getPhone())){
//            user.getUserPhone().clear();
//
//            createUserPhoneDtoList(request.getPhone(), user);
//            user = userService.save(user);
//
//            PartnerContact partnerContact = new PartnerContact();
//            partnerContact.setUser(user);
//            partnerContact.setPartner(partner);
//
//            PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId(partner.getPartEntityid(), user.getUserEntityId());
//            partnerContact.setId(partnerContactEntityId);
//            partnerContactRepository.save(partnerContact);
//        }
//        user.setUserFullName(request.getName());
//        userService.save(user);
//
//        PartnerContactEntityId id = new PartnerContactEntityId();
//        id.setPartnerId(partner.getPartEntityid());
//        id.setUserId(user.getUserEntityId());
//
//        return partnerContactRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Partner Contact Not Found"));
//    }
}
