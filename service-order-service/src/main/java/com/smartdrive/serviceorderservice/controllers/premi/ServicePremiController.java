package com.smartdrive.serviceorderservice.controllers.premi;

import com.smartdrive.serviceorderservice.dto.request.SecrReqDto;
import com.smartdrive.serviceorderservice.services.premi.ServPremiCreditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/premi")
@RequiredArgsConstructor
public class ServicePremiController {

    private final ServPremiCreditService servPremiCreditService;

    @PutMapping("/credit/update/{secrServId}/{secrId}")
    public ResponseEntity<?> updatePremiByDuedate(@Valid @RequestBody SecrReqDto secrReqDto,
                                                  @PathVariable("secrId") Long secrId,
                                                  @PathVariable("secrServId") Long secrServId){

        return new ResponseEntity<>(servPremiCreditService.updateSecr(secrReqDto, secrId, secrServId), HttpStatus.OK);
    }
}
