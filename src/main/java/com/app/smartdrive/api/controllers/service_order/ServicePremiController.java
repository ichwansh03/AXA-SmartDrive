package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.services.service_order.SecrService;
import com.app.smartdrive.api.services.service_order.SemiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/premi")
@RequiredArgsConstructor
public class ServicePremiController {

    private final SemiService semiService;
    private final SecrService secrService;

    @GetMapping("/semi")
    public ResponseEntity<?> getSemiById(@RequestParam("servId") Long servId){
        return new ResponseEntity<>(semiService.findAllBySemiServId(servId), HttpStatus.OK);
    }

    @GetMapping("/secr")
    public ResponseEntity<?> getSecrById(@RequestParam("servId") Long servId){
        return new ResponseEntity<>(secrService.findAllBySecrServId(servId), HttpStatus.OK);
    }
}
