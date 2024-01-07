package com.app.smartdrive.api.controllers.service_order.premi;

import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/premi")
@RequiredArgsConstructor
@CrossOrigin
public class ServicePremiController {

    private final ServPremiCreditService servPremiCreditService;

    @PutMapping("/credit/update/{secrServId}/{secrId}")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updatePremiByDuedate(@Valid @RequestBody SecrReqDto secrReqDto,
                                                  @PathVariable("secrId") Long secrId,
                                                  @PathVariable("secrServId") Long secrServId){

        servPremiCreditService.updateSecr(secrReqDto, secrId, secrServId);

        return new ResponseEntity<>(secrReqDto, HttpStatus.OK);
    }


}
