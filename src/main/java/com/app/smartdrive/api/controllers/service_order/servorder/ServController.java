package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.customer.request.ClaimRequestDTO;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.services.service_order.servorder.services.ServService;
import com.app.smartdrive.api.services.service_order.servorder.services.ServiceTransaction;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ServController {

    private final ServService servService;
    private final ServiceTransaction serviceTransaction;

    @GetMapping("service/feasiblity")
    public ResponseEntity<?> getAllSeroFeasiblity(){
        List<FeasiblityDto> allFeasiblity = servService.findAllFeasiblity();
        return new ResponseEntity<>(allFeasiblity, HttpStatus.OK);
    }

    @GetMapping("service/polis")
    public ResponseEntity<?> getAllSeroPolis(){
        List<PolisDto> allPolis = servService.findAllPolis();
        return new ResponseEntity<>(allPolis, HttpStatus.OK);
    }

    @GetMapping("/service")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> getServiceById(@RequestParam("servId") Long id) {

        ServiceRespDto servicesById = servService.getById(id);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(servicesById, HttpStatus.OK);
    }

    @GetMapping("/services")
    public ResponseEntity<?> getServices(){
        List<ServiceRespDto> services = servService.getAll();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping("/service")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> generateService(@Valid @RequestBody ClaimRequestDTO requestDTO) throws Exception {

        ServiceDto serviceRespDto = serviceTransaction.addService(requestDTO.getCreqEntityId());

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

}
