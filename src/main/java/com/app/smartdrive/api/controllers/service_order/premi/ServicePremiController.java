package com.app.smartdrive.api.controllers.service_order.premi;

import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.dto.service_order.request.SemiReqDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/premi")
@RequiredArgsConstructor
public class ServicePremiController {

    private final ServPremiService servPremiService;
    private final ServPremiCreditService servPremiCreditService;

    @GetMapping("/credit")
    public ResponseEntity<?> getServicePremiCredit(){
        return new ResponseEntity<>(servPremiCreditService.findByDueDate(), HttpStatus.OK);
    }

    @PutMapping("/credit/update")
    public ResponseEntity<?> updatePremiByDuedate(@Valid @RequestBody SecrReqDto secrReqDto){

        return new ResponseEntity<>(servPremiCreditService.updateSecr(secrReqDto), HttpStatus.OK);
    }
}
