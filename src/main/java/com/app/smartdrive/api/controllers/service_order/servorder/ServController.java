package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.SecrDto;
import com.app.smartdrive.api.dto.service_order.response.SemiDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServController {

    private final ServService servService;
    private final CustomerRequestService customerRequestService;

    private final ServOrderService servOrderService;
    private final ServOrderTaskService servOrderTaskService;
    private final ServOrderWorkorderService servOrderWorkorderService;

    private final ServPremiService servPremiService;
    private final ServPremiCreditService servPremiCreditService;

    SoAdapter soAdapter = new SoAdapter();

    @GetMapping
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId) {

        Services servicesById = servService.findServicesById(servId);
        CustomerResponseDTO customerRequestById = customerRequestService.getCustomerRequestById(servicesById.getCustomer().getCreqEntityId());
        List<ServiceOrders> allSeroByServId = servOrderService.findAllSeroByServId(servicesById.getServId());

        ServicePremi servicePremi = servPremiService.findByServId(servicesById.getServId());
        SemiDto semiDto = TransactionMapper.mapEntityToDto(servicePremi, SemiDto.class);

        List<ServicePremiCredit> servicePremiCredits = servPremiCreditService.findByServId(servicesById.getServId());
        List<SecrDto> secrDtoList = TransactionMapper.mapEntityListToDtoList(servicePremiCredits, SecrDto.class);
        semiDto.setSecrDtoList(secrDtoList);

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(servicesById, ServiceRespDto.class);
        serviceRespDto.setCustomerResponseDTO(customerRequestById);
        serviceRespDto.setServiceOrdersList(TransactionMapper.mapEntityListToDtoList(allSeroByServId, ServiceOrderRespDto.class));
        serviceRespDto.setSemiDto(semiDto);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

    @GetMapping("/addserv")
    public ResponseEntity<?> generateService(@RequestParam("creqId") Long creqId) throws Exception {
        Services services = servService.addService(creqId);

        ServiceRespDto serviceRespDto = soAdapter.responseServices(services);

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

}
