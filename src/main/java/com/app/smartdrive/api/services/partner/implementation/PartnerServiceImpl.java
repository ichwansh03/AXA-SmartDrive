package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.EnumUsers;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.master.CityService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final CityService cityService;
    private final UserService userService;
    private final UserRolesService userRolesService;
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
    @Transactional
    public Partner save(Partner partner) {
        if(Objects.isNull(partner.getBusinessEntity())){
            ProfileRequestDto userDto = new ProfileRequestDto();
            userDto.setUserEmail(partner.getPartName());
            userDto.setUserNpwp(partner.getPartNpwp());
            userDto.setUserFullName(partner.getPartName());
            userDto.setUserNationalId(partner.getPartNpwp());
            userDto.setUserName(partner.getPartName());

            User user = userService.createUser(userDto);

            userRolesService.createUserRole(EnumUsers.RoleName.PR, user);

            userService.save(user);
            log.info("id user for partner {}", user.getUserEntityId());
            log.info("role user {}", user.getUserRoles().stream().map(userRoles -> userRoles.getRoles().getRoleDescription()).toList().toString());
            partner.setBusinessEntity(user.getUserBusinessEntity());
        }

        partner.getBusinessEntity().setEntityModifiedDate(LocalDateTime.now());
        partnerRepository.save(partner);
        log.info("id partner {}", partner.getPartEntityid());
        log.info("id businessEntity {}", partner.getBusinessEntity().getEntityId());
        return partner;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        partnerRepository.delete(getById(id));
    }

    @Override
    public Partner create(PartnerRequest request) {
        Cities city = cityService.getById(request.getCityId());
        Partner partner = new Partner();
        partner = TransactionMapper.mapDtoToEntity(request, partner);
        partner.setPartAccountNo(request.getPartAccountNo());
        partner.setCity(city);
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
