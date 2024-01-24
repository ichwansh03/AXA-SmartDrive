package com.app.smartdrive.api.services.service_order.servorder.services.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.services.ServService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServImpl implements ServService {

    private final SoRepository soRepository;
    private final ServOrderService servOrderService;

    private final ServPremiService servPremiService;
    private final ServPremiCreditService servPremiCreditService;

    private final CustomerRequestService customerRequestService;
    private final UserService userService;

    //avoid this operation, not SRP!
    @Override
    public ServiceRespDto getById(Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + servId + " not found"));
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",services.getServId());

        CustomerRequest existCustomerRequest = customerRequestService.getById(services.getCustomer().getCreqEntityId());
        CustomerResponseDTO customerRequestById = TransactionMapper.mapEntityToDto(existCustomerRequest, CustomerResponseDTO.class);

        User user = userService.getById(services.getUsers().getUserEntityId());
        UserDto userDto = TransactionMapper.mapEntityToDto(user, UserDto.class);

        List<ServiceOrderRespDto> serviceOrderRespDtos = servOrderService.findAllOrderByServId(servId);

        SemiDto semiDto = new SemiDto();
        if (services.getServType() == EnumCustomer.CreqType.POLIS) {
            ServicePremi servicePremi = servPremiService.findByServId(services.getServId());
            semiDto = TransactionMapper.mapEntityToDto(servicePremi, SemiDto.class);
            List<SecrDto> secrDtoList = servPremiCreditService.findPremiCreditByServId(services.getServId());
            semiDto.setSecrDtoList(secrDtoList);
        }

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);

        serviceRespDto.setUserDto(userDto);
        serviceRespDto.setCustomerResponseDTO(customerRequestById);
        serviceRespDto.setServiceOrdersList(serviceOrderRespDtos);
        serviceRespDto.setSemiDto(semiDto);

        return serviceRespDto;
    }

    @Override
    public List<ServiceRespDto> getAll() {
        List<Services> services = soRepository.findAll();
        return TransactionMapper.mapEntityListToDtoList(services, ServiceRespDto.class).stream()
                .map(serviceRespDto -> getById(serviceRespDto.getServId()))
                .toList();
    }

    @Override
    public List<FeasiblityDto> findAllFeasiblity() {

        List<ServiceRespDto> serviceRespDtoList = getAll();

        return serviceRespDtoList.stream()
                .filter(type -> type.getServType().equals(EnumCustomer.CreqType.FEASIBLITY))
                .map(feasiblity -> FeasiblityDto.builder()
                        .creqId(feasiblity.getCustomerResponseDTO().getCreqEntityId())
                        .servType(feasiblity.getServType())
                        .createdOn(feasiblity.getServCreatedOn())
                        .customerName(feasiblity.getUserDto().getUserFullName())
                        .customerEmail(feasiblity.getUserDto().getUserEmail())
                        .seroId(feasiblity.getServiceOrdersList().get(0).getSeroId())
                        .servStatus(feasiblity.getServStatus()).build())
                .toList();
    }

    @Override
    public List<PolisDto> findAllPolis() {
        List<ServiceRespDto> serviceRespDtoList = getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return serviceRespDtoList.stream()
                .filter(type -> !type.getServType().equals(EnumCustomer.CreqType.FEASIBLITY))
                .map(feasiblity -> PolisDto.builder()
                        .creqId(feasiblity.getCustomerResponseDTO().getCreqEntityId())
                        .seroId(feasiblity.getServiceOrdersList().get(0).getSeroId())
                        .customerName(feasiblity.getUserDto().getUserFullName())
                        .polisNo(feasiblity.getServInsuranceno())
                        .periode(feasiblity.getServEnddate().format(formatter))
                        .vehicleNo(feasiblity.getServVehicleno())
                        .servType(feasiblity.getServType())
                        .totalPremi(feasiblity.getCustomerResponseDTO().getCustomerInscAssets().getCiasTotalPremi()).build())
                .toList();
    }

}
