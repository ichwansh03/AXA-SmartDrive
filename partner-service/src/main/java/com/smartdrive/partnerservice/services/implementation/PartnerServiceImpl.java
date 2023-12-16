package com.smartdrive.partnerservice.services.implementation;

import com.smartdrive.partnerservice.dto.request.PartnerRequest;
import com.smartdrive.partnerservice.entities.Partner;
import com.smartdrive.partnerservice.mapper.TransactionMapper;
import com.smartdrive.partnerservice.repositories.PartnerRepository;
import com.smartdrive.partnerservice.services.PartnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
//    private final CityService cityService;
//    private final UserService userService;
//    private final UserRolesService userRolesService;
    @Override
    @Transactional
    public Partner getById(Long entityId) {
        return partnerRepository.findById(entityId).orElseThrow(() -> {
            throw new EntityNotFoundException("Partner not found by id "+entityId);
        });
    }

    @Override
    @Transactional
    public List<Partner> getAll() {
        return partnerRepository.findAll();
    }

    @Override
    public Partner save(Partner partner) {
//        partner.getBusinessEntity().setEntityModifiedDate(LocalDateTime.now());
        return partnerRepository.save(partner);
    }

//    @Transactional
//    private User createUser(Partner partner, boolean grantUserAccess){
//        ProfileRequestDto userDto = new ProfileRequestDto();
//        userDto.setUserEmail(partner.getPartName());
//        userDto.setUserNpwp(partner.getPartNpwp());
//        userDto.setUserFullName(partner.getPartName());
//        userDto.setUserNationalId(partner.getPartNpwp());
//        userDto.setUserName(String.format("%15s", partner.getPartName().replaceAll(" ", ""))
//                .replaceAll(" ", "0"));
//        if (grantUserAccess){
//            userDto.setUserPassword(partner.getPartNpwp());
//        }
//
//        User user = userService.createUser(userDto);
//
//        userRolesService.createUserRole(EnumUsers.RoleName.PR, user);
//
//        return userService.save(user);
//
//    }

    @Override
    @Transactional
    public Partner save(PartnerRequest partnerRequest) {
        Partner partner = create(partnerRequest);
//        User user = createUser(partner, partnerRequest.isGrantUserAccess());
//        partner.setBusinessEntity(user.getUserBusinessEntity());
        partnerRepository.save(partner);
        return partner;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        partnerRepository.delete(getById(id));
    }

    @Override
    public Partner create(PartnerRequest request) {
//        Cities city = cityService.getById(request.getCityId());
        Partner partner = new Partner();
        partner = TransactionMapper.mapDtoToEntity(request, partner);
        partner.setPartAccountNo(request.getPartAccountNo());
//        partner.setCity(city);
        return partner;
    }

    @Override
    @Transactional
    public Page<Partner> searchByNameOrNpwp(String value, int page) {
        Pageable pageable = PageRequest.of(page, 2);
        Page<Partner> partnerWithNameOrNpwpByValue;
        partnerWithNameOrNpwpByValue = partnerRepository.findAllByPartNameContainingOrPartNpwpContaining(value, value, pageable);
        return partnerWithNameOrNpwpByValue;
    }
}
